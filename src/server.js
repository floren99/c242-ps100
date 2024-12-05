const Hapi = require('@hapi/hapi');
const routes = require('./routes/routes'); // Replace with your actual routes file

const init = async () => {
  try {
    const server = Hapi.server({
      port: process.env.PORT || 8080,
      host: '0.0.0.0',
      routes: {
        cors: {
          origin: ['*'],
        },
      },
    });

    // Define routes
    server.route(routes);

    // Start the server
    await server.start();
    console.log(`Server berjalan pada ${server.info.uri}`);
  } catch (error) {
    console.error('Gagal memulai server:', error);
  }
};

init();
