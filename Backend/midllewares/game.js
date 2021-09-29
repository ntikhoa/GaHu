const mongoose = require('mongoose')
const Joi = require('joi');
const moment = require('moment');
const imageSize = require('image-size');
const Game = require('../models/game');
const Platform = require('../models/platform');
const ExpressError = require('../utils/ExpressError');
const Constants = require('../utils/Constants');
const { validateSchema } = require('../utils/validate');


module.exports.validateCreateGameBody = (req, res, next) => {
    validateSchema(createGameSchema, req.body);
    if (req.body.releaseDate) {
        validateDateFormat(req.body.releaseDate);
    }
    if (!req.file) {
        throw new ExpressError('Image is not provided', Constants.BAD_REQUEST, 400);
    } else {
        validateImageRatio(req.file.buffer);
    }

    next();
}

const createGameSchema = Joi.object({
    title: Joi.string().trim().required(),
    description: Joi.string().trim().min(50).required(),
    platformIds: Joi.array().items(Joi.string()).required(),
    releaseDate: Joi.string().trim()
});

const validateDateFormat = (date) => {
    if (!moment(date, 'YYYY-MM-DD', true).isValid()) {
        throw new ExpressError('Invalid date format YYYY-MM-DD',
            Constants.BAD_REQUEST, 400);
    }
}

const validateImageRatio = (imagePath) => {
    const imageDimension = imageSize(imagePath);
    const ratio = imageDimension.width / imageDimension.height;
    if (ratio < (Constants.IMAGE_RATIO - 0.2)
        || ratio > (Constants.IMAGE_RATIO + 0.2)) {
        throw new ExpressError('Invalid image ratio', Constants.BAD_REQUEST, 400);
    }
}

module.exports.validatePlatformIds = async (req, res, next) => {
    const { platformIds } = req.body;
    if (platformIds) {
        for (const platformId of platformIds) {
            if (!mongoose.Types.ObjectId.isValid(platformId)) {
                throw new ExpressError("Invalid platform ids", Constants.BAD_REQUEST, 400);
            }
        }
        const platformIdObjects = platformIds.map(el => {
            return mongoose.Types.ObjectId(el);
        })

        const result = await Platform.find()
            .where('_id')
            .in(platformIdObjects)
            .select({ 'name': 1 })
            .exec();

        if (result.length < platformIds.length) {
            throw new ExpressError("Platforms not found", Constants.NOT_FOUND, 404);
        }

        req.platformIdObjects = platformIdObjects;
        req.platforms = result;
    }
    next();
}

module.exports.validateGetGameDetail = async (req, res, next) => {
    const { id } = req.params;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        throw new ExpressError("Invalid game id", Constants.BAD_REQUEST, 400);
    }

    const game = await Game.findById(id)
        .populate('platforms', 'name')
        .populate('author', 'username email');
    if (!game) {
        throw new ExpressError("Game not found", Constants.NOT_FOUND, 404);
    }
    req.game = game;
    next();
}


module.exports.isGameAuthor = (req, res, next) => {
    const user = req.user;
    const game = req.game;
    if (!game.author._id.equals(user._id)) {
        throw new ExpressError("Not allowed", Constants.FORBIDDEN, 403);
    }
    next();
}

module.exports.validateUpdateGameBody = (req, res, next) => {
    validateSchema(updateGameSchema, req.body);
    if (req.body.releaseDate) {
        validateDateFormat(req.body.releaseDate);
    }
    if (req.file) {
        validateImageRatio(req.file.buffer);
    }

    next();
}

const updateGameSchema = Joi.object({
    title: Joi.string().trim(),
    description: Joi.string().trim().min(50),
    platformIds: Joi.array().items(Joi.string()),
    releaseDate: Joi.string().trim()
});

module.exports.validateGetGamesPagination = async (req, res, next) => {
    validateSchema(getGamesPaginationSchema, req.query);
    const platformId = req.query.platformId;
    if (platformId) {
        if (!mongoose.Types.ObjectId.isValid(platformId)) {
            throw new ExpressError("Invalid platform id", Constants.BAD_REQUEST, 400);
        }
        const platform = await Platform.findById(platformId);
        if (!platform) {
            throw new ExpressError("Platform not found", Constants.NOT_FOUND, 404);
        }
    }
    next();
}

const getGamesPaginationSchema = Joi.object({
    page: Joi.number().min(1).required(),
    platformId: Joi.string()
})