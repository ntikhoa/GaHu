const mongoose = require('mongoose')
const Joi = require('joi');
const moment = require('moment');
const Game = require('../models/game');
const ExpressError = require('../utils/ExpressError');
const Constants = require('../utils/Constants');
const { validateSchema } = require('../utils/validate');


module.exports.validateCreateGameBody = (req, res, next) => {
    validateSchema(createGameSchema, req.body);
    if (req.body.releaseDate) {
        if (!moment(req.body.releaseDate, 'YYYY-MM-DD', true).isValid()) {
            throw new ExpressError('Invalid date format YYYY-MM-DD',
                Constants.BAD_REQUEST, 400);
        }
    }
    if (!req.file) {
        throw new ExpressError('Image is not provided', Constants.BAD_REQUEST, 400);
    }
    next();
}

const createGameSchema = Joi.object({
    title: Joi.string().trim().required(),
    description: Joi.string().trim().min(50).required(),
    platformIds: Joi.array().items(Joi.string()).required(),
    releaseDate: Joi.string().trim()
});

module.exports.validateGetGameDetail = async (req, res, next) => {
    const { id } = req.params;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        throw new ExpressError("Invalid game id", Constants.BAD_REQUEST, 400);
    }

    const game = await Game.findById(id)
        .populate('platforms')
        .populate('author');
    if (!game) {
        throw new ExpressError("Game not found", Constants.NOT_FOUND, 404);
    }
    req.game = game;
    next();
}