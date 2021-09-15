const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/user');
const Constants = require('../utils/constants');

exports.register = async (req, res, next) => {
    const { email, username, password } = req.body;

    const hashedPassword = await bcrypt.hash(password, Constants.SALT);
    const user = new User({
        email: email,
        username: username,
        password: hashedPassword
    });
    const result = await user.save();

    const token = createToken(result._id, hashedPassword);

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

exports.login = async (req, res, next) => {
    const user = req.user;

    const token = createToken(user._id, user.password);

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
    })
}

function createToken(userId, hashedPassword) {
    return jwt.sign({
        userId: userId,
        hashedPassword: hashedPassword
    }, Constants.SECRET_SIGNATURE);
}