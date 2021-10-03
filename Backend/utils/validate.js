const ExpressError = require("./ExpressError");

exports.validateSchema = (schema, body) => {
    const { error } = schema.validate(body);
    if (error) {
        const msg = error.details.map(el => el.message).join(',');
        throw new ExpressError(msg, 400);
    }
}