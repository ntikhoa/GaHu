const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const gameSchema = new Schema({
    title: {
        type: String,
        required: true
    },
    releaseDate: {
        type: String
    },
    image: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true,
        min: 50
    },
    platforms: [
        {
            type: Schema.Types.ObjectId,
            ref: 'Platform',
            required: true
        }
    ],
    author: {
        type: Schema.Types.ObjectId,
        ref: 'User',
        required: true
    }
}, {
    timestamps: {
        createdAt: 'created_at',
        updatedAt: 'updated_at'
    }
});

module.exports = mongoose.model('Game', gameSchema);