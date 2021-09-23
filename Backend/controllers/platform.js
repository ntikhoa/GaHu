const Platform = require('../models/platform');


module.exports.getAllPlatform = async (req, res, next) => {
    let platforms = await Platform.find({});
    platforms = platforms.map(platform => {
        return {
            id: platform._id,
            name: platform.name
        };
    });

    res.status(200).json({
        status: 200,
        data: platforms,
        error: null,
        message: "Get platforms successfully"
    });
}