const joi = require("joi");
const Constants = require("../utils/constants");
const ExpressError = require("../utils/ExpressError");
const User = require("../models/user");
const { validateSchema } = require("../utils/validate");

exports.validateUpdateUser = (req, res, next) => {
    validateSchema(updateUserSchema, req.body);
    next();
}

function validate(body) {
    const { error } = updateUserSchema.validate(body);
    if (error) {
        const msg = error.details.map(el => el.message).join(',');
        throw new ExpressError(msg, Constants.BAD_REQUEST, 400);
    }
}

const updateUserSchema = joi.object({
    email: joi.string().lowercase().email().required(),
    username: joi.string().max(16).required()
});

exports.checkDuplicateUpdatedEmail = async (req, res, next) => {
    const email = req.body.email;
    const user = req.user;

    const foundUser = await User.findOne({ email: email, '_id': { $ne: user._id } });
    if (foundUser) {
        throw new ExpressError("Email already exist", Constants.BAD_REQUEST, 400);
    }
    next();
}