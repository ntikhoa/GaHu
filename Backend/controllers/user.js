module.exports.getUser = (req, res, next) => {
    const user = req.user;

    res.status(200).json({
        data: {
            id: user._id,
            email: user.email,
            username: user.username
        },
        message: 'Get user info successfully'
    });
}

module.exports.updateUser = async (req, res, next) => {
    const { email, username } = req.body;
    const user = req.user;

    user.email = email;
    user.username = username;
    const result = await user.save();

    res.status(200).json({
        data: {
            id: result._id,
            email: result.email,
            username: result.username
        },
        message: 'Update user successfully'
    });
}