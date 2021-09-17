const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const platformSchema = new Schema({
    name: {
        type: String,
        required: true
    }
}, {
    timestamps: {
        createdAt: 'created_at',
        updatedAt: 'updated_at'
    }
});

module.exports = mongoose.model('Platform', platformSchema);