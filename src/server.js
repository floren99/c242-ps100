const Hapi = require('@hapi/hapi');
const questionRoutes = require('./routes/questionRoutes');

const init = async () => {
  const server = Hapi.server({
    port: 9000,
    host: 'localhost'
  });

  server.route(questionRoutes);  // Tambahkan route questions di sini

  await server.start();
  console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
  console.log(err);
  process.exit(1);
});

init();

// const Hapi = require('@hapi/hapi');
// const routes = require('./routes');

// const init = async () => {
//   const server = Hapi.server({
//     port: 9000,
//     host: 'localhost',
//   });

//   server.route(routes);

//   await server.start();
//   console.log(`Server running on ${server.info.uri}`);
// };

// init();
