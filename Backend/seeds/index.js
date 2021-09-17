const mongoose = require('mongoose');
const Platform = require('../models/platform');

mongoose.connect('mongodb://localhost:27017/game-info',
    {
        useNewUrlParser: true,
        useUnifiedTopology: true
    });

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'CONNECTION ERROR:'))
db.once('open', () => {
    console.log('CONNECTION OPEN')
});

const name = [
    'PS5', 'PS4', 'Xbox', 'PC', 'Nintendo Switch'
]

async function seedPlatform() {
    await Platform.deleteMany({});
    const promises = [];
    for (let i = 0; i < 5; i++) {
        const platform = new Platform({
            name: name[i]
        })
        promises.push(platform.save());
    }
    await Promise.all(promises);
}

seedPlatform().then(() => {
    db.close();
})