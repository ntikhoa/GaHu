const mongoose = require('mongoose');
const Constants = require("../utils/Constants");
const Game = require('../models/game');
const { removeImage } = require('../utils/removeImage');

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
    });

    res.status(201).json({
        status: 201,
        data: {
            _id: result._id,
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

module.exports.getGameDetail = async (req, res, next) => {
    const game = req.game;

    const platforms = game.platforms.map(el => {
        return {
            id: el._id,
            name: el.name
        }
    });

    const isAuthor = req.user._id.equals(game.author._id);

    res.status(200).json({
        status: 200,
        data: {
            isAuthor: isAuthor,
            id: game._id,
            title: game.title,
            releaseDate: game.releaseDate,
            description: game.description,
            platforms: platforms,
            image: Constants.BASE_URL + game.image,
            author: {
                id: game.author._id,
                username: game.author.username
            }
        },
        error: null,
        message: 'Get game details successfully'
    });
}

module.exports.deleteGame = async (req, res, next) => {
    const game = req.game;
    removeImage(game.image);
    const result = await Game.findByIdAndRemove(game._id);
    console.log(result);
    res.status(200).json({
        status: 200,
        data: {
            id: result._id
        },
        error: null,
        message: 'Delete successfully'
    });
}