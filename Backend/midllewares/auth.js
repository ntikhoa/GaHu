const Joi = require("joi");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const User = require("../models/user");
const Constants = require("../utils/Constants");
const ExpressError = require("../utils/ExpressError");
const { validateSchema } = require("../utils/validate");

module.exports.validateRegister = (req, res, next) => {
    validateSchema(registerBodySchema, req.body);
    next();
}

const registerBodySchema = Joi.object({
    email: Joi.string().trim().lowercase().email().required(),
    username: Joi.string().trim().max(16).required(),
    password: Joi.string().min(8).required(),
    confirmPassword: Joi.string().required()
});

module.exports.isPasswordConfirmMatched = (req, res, next) => {
    const { password, confirmPassword } = req.body;
    checkPasswordConfirmation(password, confirmPassword);
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

module.exports.isEmailExist = async (req, res, next) => {
    const { email } = req.body;

    const user = await User.findOne({ email: email });
    if (!user) {
        throw new ExpressError('Email does not exist', Constants.NOT_FOUND, 404);
    }
    req.user = user;
    next();
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

module.exports.isNewPasswordConfirmMatched = (req, res, next) => {
    const { newPassword, newConfirmPassword } = req.body;
    checkPasswordConfirmation(newPassword, newConfirmPassword);
    next();
}

function checkPasswordConfirmation(password, confirmPassword) {
    if (password !== confirmPassword) {
        throw new ExpressError('Confirm password is not matched with password',
            Constants.BAD_REQUEST,
            300);
    }
}

module.exports.validateNewPassword = (req, res, next) => {
    const { newPassword } = req.body;
    validateSchema(Joi.string().min(8).required(), newPassword);
    next();
}

module.exports.isAuth = async (req, res, next) => {
    const authHeader = req.get('Authorization');
    if (!authHeader) {
        throw new ExpressError('Not authenticated', Constants.UNAUTHORIZED, 401);
    }
    const bearer = authHeader.split(' ')[0];
    const token = authHeader.split(' ')[1];
    if (!token || bearer !== 'Bearer') {
        throw new ExpressError('Invalid token', Constants.BAD_REQUEST, 400);
    }
    decodedToken = jwt.verify(token, Constants.SECRET_SIGNATURE);

    const user = await User.findById(decodedToken.userId);
    if (!user) {
        console.log('cannot find user');
        throw new ExpressError('Invalid token', Constants.BAD_REQUEST, 400);
    }
    console.log(decodedToken.createdAt);
    console.log(user.passwordChangedAt);
    if (decodedToken.createdAt !== user.passwordChangedAt) {
        console.log('password change');
        throw new ExpressError('Invalid token', Constants.BAD_REQUEST, 400);
    }

    req.user = user;
    next();
}

module.exports.isAdmin = (req, res, next) => {
    const user = req.user;
    if (!Constants.ADMIN_EMAILS.includes(user.email)) {
        throw new ExpressError('Only admin can use this feature', Constants.FORBIDDEN, 403);
    }
    next();
}