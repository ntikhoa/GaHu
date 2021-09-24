const mongoose = require('mongoose');
const Constants = require("../utils/Constants");
const Game = require('../models/game');
const { removeImage } = require('../utils/removeImage');

module.exports.createGame = async (req, res, next) => {
    const { title, releaseDate, description } = req.body;
    const platforms = req.platforms;
    const platformIdObjects = req.platformIdObjects;
    const user = req.user;
    const file = req.file;

    const imageUrl = file.path.replace("\\", '/');

    const game = new Game({
        title: title,
        releaseDate: releaseDate,
        description: description,
        platforms: platformIdObjects,
        image: imageUrl,
        author: user._id
    });

    const result = await game.save();

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
                id: user._id,
                email: user.email,
                username: user.username
            }
        },
        error: null,
        message: 'Create game successfully'
    });
}

module.exports.getGameDetail = async (req, res, next) => {
    const game = req.game;

    const isAuthor = req.user._id.equals(game.author._id);

    res.status(200).json({
        status: 200,
        data: {
            isAuthor: isAuthor,
            id: game._id,
            title: game.title,
            releaseDate: game.releaseDate,
            description: game.description,
            platforms: game.platforms,
            image: Constants.BASE_URL + game.image,
            author: game.author
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

module.exports.updateGame = async (req, res, next) => {
    const { title, releaseDate, description } = req.body;
    let platforms = req.platforms;
    const platformIdObjects = req.platformIdObjects;
    const file = req.file;
    const game = req.game;

    if (title) {
        game.title = title;
    }
    if (releaseDate) {
        game.releaseDate = releaseDate;
    }
    if (description) {
        game.description = description;
    }
    if (platformIdObjects) {
        game.platforms = platformIdObjects;
    }
    if (file) {
        removeImage(game.image);
        const imageUrl = file.path.replace("\\", '/');
        game.image = imageUrl;
    }

    await game.save();

    if (!platforms) {
        platforms = game.platforms;
    }

    res.status(201).json({
        status: 201,
        data: {
            _id: game._id,
            title: game.title,
            releaseDate: game.releaseDate,
            description: game.description,
            platforms: platforms,
            image: Constants.BASE_URL + game.image,
            author: game.author
        },
        error: null,
        message: 'Update game successfully'
    });

}

module.exports.getGames = async (req, res, next) => {
    const game = await Game.find({}).limit(1);
    await Game.populate(game, { path: 'platforms', select: 'name' })
        .populate(game, { path: 'author', select: 'username email' });
    res.json({
        game: game
    });
}