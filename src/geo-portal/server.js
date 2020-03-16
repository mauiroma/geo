
// Hello-world for nodejs

var http         =   require('http');
var serverPort   =   process.env.MY_SERVER_PORT      || 8080;

var server = http.createServer(function (request, response) {
  response.writeHead(200, {"Content-Type": "text/plain"});
  response.end("Hello World!!!\n");
});

server.listen(serverPort);
console.log("Server running port: " + serverPort);