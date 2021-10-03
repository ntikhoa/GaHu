const Platform = require('../models/platform');


module.exports.getAllPlatform = async (req, res, next) => {
    let platforms = await Platform.find({}).select({ 'name': 1 });

    res.status(200).json({
        data: platforms,
        message: "Get platforms successfully"
    });
}