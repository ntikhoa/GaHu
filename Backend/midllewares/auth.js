const Joi = require("joi");
const { emit } = require("../models/user");
const User = require("../models/user");
const Constants = require("../utils/constants");
const ExpressError = require("../utils/ExpressError");

module.exports.validateRegister = (req, res, next) => {
    const { error } = registerBodySchema.validate(req.body);
    if (error) {
        const msg = error.details.map(el => el.message).join(',')
        throw new ExpressError(msg, Constants.BAD_REQUEST, 400);
    }
    next();
}

module.exports.checkExistedUserByEmail = async (req, res, next) => {
    const email = req.body.email;
    const user = await User.findOne({ email: email });
    if (user) {
        throw new ExpressError("Email already exist", Constants.BAD_REQUEST, 400);
    }
    next();
}

module.exports.isPasswordConfirmMatched = (req, res, next) => {
    const { password, confirmPassword } = req.body;
    if (password !== confirmPassword) {
        throw new ExpressError('Confirm password is not matched with password',
            Constants.BAD_REQUEST,
            300);
    }
    next();
}

const registerBodySchema = Joi.object({
    email: Joi.string().lowercase().email().required(),
    username: Joi.string().max(16).required(),
    password: Joi.string().min(8).required(),
    confirmPassword: Joi.string().required()
})