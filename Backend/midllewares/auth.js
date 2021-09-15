const Joi = require("joi");
const bcrypt = require("bcrypt");
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

module.exports.isPasswordConfirmMatched = (req, res, next) => {
    const { password, confirmPassword } = req.body;
    if (password !== confirmPassword) {
        throw new ExpressError('Confirm password is not matched with password',
            Constants.BAD_REQUEST,
            300);
    }
    next();
}

module.exports.checkRegisteredUserByEmail = async (req, res, next) => {
    const email = req.body.email;
    const user = await User.findOne({ email: email });
    if (user) {
        throw new ExpressError("Email already exist", Constants.BAD_REQUEST, 400);
    }
    next();
}

const registerBodySchema = Joi.object({
    email: Joi.string().lowercase().email().required(),
    username: Joi.string().max(16).required(),
    password: Joi.string().min(8).required(),
    confirmPassword: Joi.string().required()
})

module.exports.isEmailExist = async (req, res, next) => {
    const { email } = req.body;

    const user = await User.findOne({ email: email });
    if (!user) {
        throw new ExpressError('Email does not exist', Constants.NOT_FOUND, 404);
    }
    req.user = user;
    next()
}

module.exports.isPasswordCorrect = async (req, res, next) => {
    const { password } = req.body;
    const user = req.user;

    const isEqual = await bcrypt.compare(password, user.password);
    if (!isEqual) {
        throw new ExpressError('Wrong password', Constants.UNAUTHORIZED, 401);
    }
    next();
}