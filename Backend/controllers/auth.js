const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/user');
const Constants = require('../utils/constants');

exports.register = async (req, res, next) => {
    const { email, username, password, confirmPassword } = req.body;

    const hashedPassword = await bcrypt.hash(password, 12);
    const user = new User({
        email: email,
        username: username,
        password: hashedPassword
    });
    const result = await user.save();
    console.log(result);
    const token = jwt.sign({
        userId: result._id,
        hashedPassword: hashedPassword
    }, Constants.SECRET_SIGNATURE);
    res.status(201).json({
        status: 201,
        data: {
            token: token,
            user: user
        },
        error: null,
        message: 'Register succesfully.'
    });
}