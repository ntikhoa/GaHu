const ExpressError = require("./ExpressError");
const Constants = require("./constants");

exports.validateSchema = (schema, body) => {
    const { error } = schema.validate(body);
    if (error) {
        const msg = error.details.map(el => el.message).join(',');
        throw new ExpressError(msg, Constants.BAD_REQUEST, 400);
    }
}