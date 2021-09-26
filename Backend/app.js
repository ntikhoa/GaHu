const express = require('express');
const mongoose = require('mongoose');
const path = require('path');
const helmet = require('helmet');
const compression = require('compression');

const authRoutes = require('./routes/auth');
const userRoutes = require('./routes/user');
const platformRoutes = require('./routes/platform');
const gameRoutes = require('./routes/game');
const { removeImage } = require('./utils/removeImage');
const Constants = require('./utils/Constants');


const app = express();
app.use(express.urlencoded({ extended: true })); // x-www-form-urlencoded
app.use(express.json()); //application/json

app.use('/images', express.static(path.join(__dirname, 'images')));

app.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
    next();
});

app.use('/auth', authRoutes);
app.use('/user', userRoutes);
app.use('/platforms', platformRoutes);
app.use('/games', gameRoutes);

app.use(helmet());
app.use(compression());

app.use((err, req, res, next) => {
    const statusCode = err.statusCode || 500;
    var message = err.message;
    var error = err.error;
    console.log(message);
    if (req.file) {
        removeImage(req.file.path.replace("\\", '/'));
    }
    if (statusCode == 500 || !message) message = "(500) Something went wrong in the server";
    if (statusCode == 500 || !error) error = "Server errors"
    res.status(statusCode).json({
        status: statusCode,
        data: null,
        error: error,
        message: message
    });
});

mongoose.connect(`mongodb+srv://${process.env.MONGO_USERNAME}:${process.env.MONGO_PASSWORD}@cluster0.pv0tn.mongodb.net/${process.env.MONGO_DB_NAME}?retryWrites=true&w=majority`,
    {
        useNewUrlParser: true,
        useUnifiedTopology: true
    });

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'CONNECTION ERROR:'))
db.once('open', () => {
    console.log('CONNECTION OPEN');
    app.listen(process.env.PORT || 3000, () => {
        console.log('LISTENING ON PORT 3K');
    });
});