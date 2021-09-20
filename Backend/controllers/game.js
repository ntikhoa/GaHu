const mongoose = require('mongoose');
const Constants = require("../utils/Constants");
const ExpressError = require("../utils/ExpressError");
const Game = require('../models/game');

module.exports.createGame = async (req, res, next) => {
    const { title, releaseDate, description, platformIds } = req.body;
    const user = req.user;
    const file = req.file;

    const imageUrl = file.path.replace("\\", '/');
    const platformIdsObject = platformIds.map(el => {
        return mongoose.Types.ObjectId(el);
    });

    const game = new Game({
        title: title,
        releaseDate: releaseDate,
        description: description,
        platforms: platformIdsObject,
        image: imageUrl,
        author: user._id
    });

    const result = await game.save();
    const platformPopulatePromise = Game.populate(result, { path: 'platforms' });
    const authorPopulatePromise = Game.populate(result, { path: 'author' });
    await Promise.all([platformPopulatePromise, authorPopulatePromise]);

    const platforms = result.platforms.map(el => {
        return {
            id: el._id,
            name: el.name
        }
    })

    res.status(201).json({
        status: 201,
        data: {
            title: result.title,
            releaseDate: result.releaseDate,
            description: result.description,
            platforms: platforms,
            image: Constants.BASE_URL + result.image,
            author: {
                id: result.author._id,
                email: result.author.email,
                username: result.author.username
            }
        },
        error: null,
        message: 'Create game successfully'
    });
}