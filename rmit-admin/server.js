//Install express server
const express = require('express'),path = require('path');

const app = express();

// Serve only the static files form the dist directory
app.use(express.static(__dirname + '/dist/rmit'));

app.get('/*', function(req,res) {
    
res.sendFile(path.join(__dirname+'/dist/rmit/index.html'));
});

// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 8899);
