const Joi = require("joi");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const User = require("../models/user");
const Constants = require("../utils/constants");
const ExpressError = require("../utils/ExpressError");
const { validateSchema } = require("../utils/validate");

module.exports.validateRegister = (req, res, next) => {
    validateSchema(registerBodySchema, req.body);
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
});

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

module.exports.isAuth = async (req, res, next) => {
    const authHeader = req.get('Authorization');
    console.log(authHeader);
    if (!authHeader) {
        throw new ExpressError('Not authenticated', Constants.UNAUTHORIZED, 401);
    }
    const bearer = authHeader.split(' ')[0];
    const token = authHeader.split(' ')[1];
    if (!token || bearer !== 'Bearer') {
        throw new ExpressError('Invalid token', Constants.BAD_REQUEST, 400);
    }
    decodedToken = jwt.verify(token, Constants.SECRET_SIGNATURE);

    console.log(decodedToken.userId);

    const user = await User.findById(decodedToken.userId);
    if (!user) {
        throw new ExpressError('Invalid token', Constants.BAD_REQUEST, 400);
    }
    req.user = user;
    next();
}