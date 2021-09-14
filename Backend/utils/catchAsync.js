const ExpressError = require('./ExpressError')

module.exports = fn => {
    return function (req, res, next) {
        fn(req, res, next).catch(next);
    }
}