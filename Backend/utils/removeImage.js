const fs = require('fs');

module.exports.removeImage = (name) => {
    fs.unlinkSync(name);
}