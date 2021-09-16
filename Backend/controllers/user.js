module.exports.getUser = (req, res, next) => {
    const user = req.user;

    res.status(200).json({
        status: 200,
        data: {
            id: user._id,
            email: user.email,
            username: user.username
        },
        error: null,
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
        status: 200,
        data: {
            id: result._id,
            email: result.email,
            username: result.username
        },
        error: null,
        message: 'Update user successfully'
    });
}