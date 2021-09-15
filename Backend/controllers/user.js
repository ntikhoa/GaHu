exports.getUserInfo = (req, res, next) => {
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