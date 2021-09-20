const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/user');
const Constants = require('../utils/Constants');

module.exports.register = async (req, res, next) => {
    const { email, username, password } = req.body;

    const currentTime = Date.now();

    const hashedPassword = await bcrypt.hash(password, Constants.SALT);
    const user = new User({
        email: email,
        username: username,
        password: hashedPassword,
        passwordChangedAt: currentTime,
    });
    const result = await user.save();

    const token = createToken(result._id, currentTime);

    res.status(201).json({
        status: 201,
        data: {
            token: token,
            id: result._id,
            email: result.email,
            username: result.username
        },
        error: null,
        message: 'Register successfully'
    });
}

module.exports.login = (req, res, next) => {
    const user = req.user;

    const token = createToken(user._id, user.passwordChangedAt);

    res.status(200).json({
        status: 200,
        data: {
            token: token,
            id: user._id,
            email: user.email,
            username: user.username
        },
        error: null,
        message: 'Login successfully'
    });
}

module.exports.changePassword = async (req, res, next) => {
    const { newPassword } = req.body;
    const user = req.user;

    const currentTime = Date.now();

    const hashedPassword = await bcrypt.hash(newPassword, Constants.SALT);

    user.password = hashedPassword;
    user.passwordChangedAt = currentTime;

    await user.save();

    const token = createToken(user._id, currentTime);

    res.status(200).json({
        status: 200,
        data: {
            token: token
        },
        error: null,
        message: 'Change password successfully'
    });
}

function createToken(userId, currentTime) {
    return jwt.sign({
        userId: userId,
        createdAt: currentTime,
    }, Constants.SECRET_SIGNATURE);
}