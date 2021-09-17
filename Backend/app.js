const express = require('express');
const mongoose = require('mongoose');

const authRoutes = require('./routes/auth');
const userRoutes = require('./routes/user');
const platformRoutes = require('./routes/platform');


const app = express();
app.use(express.urlencoded({ extended: true })); // x-www-form-urlencoded
app.use(express.json()); //application/json

app.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'OPTIONS, GET, POST, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
    next();
});

app.use('/auth', authRoutes);
app.use('/user', userRoutes);
app.use('/platforms', platformRoutes);

app.use((err, req, res, next) => {
    const statusCode = err.statusCode || 500;
    var message = err.message;
    var error = err.error;
    console.log(message);
    if (statusCode == 500 || !message) message = "(500) Something went wrong in the server";
    if (statusCode == 500 || !error) error = "Server errors"
    res.status(statusCode).json({
        status: statusCode,
        data: null,
        error: error,
        message: message
    });
});

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


app.listen(3000, () => {
    console.log('LISTENING ON PORT 3K');
});