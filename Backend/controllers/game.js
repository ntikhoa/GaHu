const Constants = require("../utils/Constants");
const Game = require('../models/game');
const { uploadToS3, deleteFromS3 } = require('../utils/s3');
const mongoose = require('mongoose');

module.exports.createGame = async (req, res, next) => {
    const { title, releaseDate, description } = req.body;
    const platforms = req.platforms;
    const platformIdObjects = req.platformIdObjects;
    const user = req.user;
    const file = req.file;


    const uploadedObject = await uploadToS3(file);
    const imageUrl = uploadedObject.Location;

    const game = new Game({
        title: title,
        releaseDate: releaseDate,
        description: description,
        platforms: platformIdObjects,
        image: uploadedObject.Key,
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
            image: imageUrl,
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
            image: Constants.IMAGE_BASE_URL + game.image,
            author: game.author
        },
        error: null,
        message: 'Get game details successfully'
    });
}

module.exports.deleteGame = async (req, res, next) => {
    const game = req.game;
    const result = await Game.findByIdAndRemove(game._id);
    await deleteFromS3(result.image);
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
        const deletePromise = deleteFromS3(game.image);
        const uploadPromise = uploadToS3(file);

        const uploadedObject = (await Promise.all([deletePromise, uploadPromise]))[1];

        game.image = uploadedObject.Key;
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
            image: Constants.IMAGE_BASE_URL + game.image,
            author: game.author
        },
        error: null,
        message: 'Update game successfully'
    });

}

module.exports.getGames = async (req, res, next) => {
    const { page, platformId } = req.query;
    let games;
    if (platformId) {
        const platformIdObject = mongoose.Types.ObjectId(platformId);

        games = await Game.find({ "platforms": platformIdObject })
            .skip((page - 1) * Constants.PER_PAGE)
            .limit(Constants.PER_PAGE)
            .select('title releaseDate description platforms image author')
            .populate('platforms', 'name')
            .populate('author', 'username email');
    } else {
        games = await Game.find({})
            .skip((page - 1) * Constants.PER_PAGE)
            .limit(Constants.PER_PAGE)
            .select('title releaseDate description platforms image author')
            .populate('platforms', 'name')
            .populate('author', 'username email');
    }

    if (!games || games.length == 0) {
        res.status(200).json({
            status: 200,
            data: null,
            error: null,
            message: 'Exhausted'
        })
    }

    games = games.map(el => {
        el.image = Constants.IMAGE_BASE_URL + el.image;
        return el;
    })

    res.status(200).json({
        status: 200,
        data: {
            page: page,
            games: games
        },
        error: null,
        message: 'Get games successfully'
    });
}