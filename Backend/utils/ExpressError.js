class ExpressError extends Error {
    constructor(message, error, statusCode) {
        super(message, statusCode);
        this.message = message;
        this.statusCode = statusCode;
        this.error = error;
    }
}

module.exports = ExpressError;