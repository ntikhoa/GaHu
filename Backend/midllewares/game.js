const { validateSchema } = require('../utils/validate');
const Joi = require('joi');
const moment = require('moment');
const ExpressError = require('../utils/ExpressError');
const Constants = require('../utils/Constants');


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