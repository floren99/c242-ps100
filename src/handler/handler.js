const { nanoid } = require("nanoid"); // randomize user id
const bcrypt = require("bcrypt");
const users = require("../users");

const { Storage } = require("@google-cloud/storage");
const path = require("path");
const fs = require("fs");
const storage = new Storage({
  keyFilename: path.join(__dirname, "../service-account-file.json"), //(IMPORTANT)
});
const bucketName = "your-bucket-name"; // Ganti dengan nama bucket Anda (IMPORTANT)

// Page Register
const saveRegisterInfoHandler = async (request, h) => {
  const { username, birthdate, password } = request.payload;

  // Validasi jika username, birthdate, atau password tidak diisi
  if (!username || !birthdate || !password) {
    const response = h.response({
      status: "fail",
      message: "Gagal membuat akun. Pastikan semua data terisi dengan benar",
    });
    response.code(400);
    return response;
  } else{
    
  }

  try {
    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Membuat user baru
    const userId = nanoid(16);
    const newUser = {
      userId,
      username,
      birthdate,
      password: hashedPassword,
    };
    users.push(newUser);

    const response = h.response({
      status: "success",
      message: "Akun berhasil dibuat",
      data: {
        userId,
      },
    });
    response.code(201);
    return response;

  } catch (error) {
    const response = h.response({
      status: "error",
      message: "Akun gagal dibuat, terjadi kesalahan server",
    });
    response.code(500);
    return response;
  }
};


const getProfileByIdHandler = (request, h) => {
  const { userId } = request.params;

  // Mencari user berdasarkan ID
  const user = users.find((n) => n.userId === userId);

  // Validasi jika profile didapatkan
  if (user !== undefined) {
    const response = h.response({
      status: "success",
      data: {
        user: {
          id: user.userId,
          username: user.username,
          birthdate: user.birthdate,
          profilePicture: user.profilePicture,
        },
      },
    });
    response.code(200);
    return response;
  }

  // Validasi jika profile tidak bisa ditampilkan
  const response = h.response({
    status: "fail",
    message: "Profie tidak bisa ditampilkan",
  });
  response.code(404);
  return response;
};

const editProfileByIdHandler = (request, h) => {
  const { userId } = request.params;

  const { username, birthdate, profilePicture } = request.payload;

  // Validasi properti `username`
  if (!username) {
    const response = h.response({
      status: "fail",
      message: "Gagal memperbarui profile. Mohon isi username",
    });
    response.code(400);
    return response;
  }

  const index = users.findIndex((user) => user.userId === userId);

  if (index !== -1) {
    users[index] = {
      ...users[index],
      username,
      birthdate,
      profilePicture,
    };

    const response = h.response({
      status: "success",
      message: "Profile berhasil diperbarui",
    });
    response.code(200);
    return response;
  }

  // Validasi jika profile tidak bisa ditampilkan
  const response = h.response({
    status: "fail",
    message: "Gagal memperbarui profile. UserId tidak ditemukan",
  });
  response.code(404);
  return response;
};

const saveUserProfilePictureHandler = async (request, h) => {
  const { profilePicture, userId } = request.payload;
  const bucket = gcs.bucket(bucketName);

  // Jika Username Gagal Kosong
  if (!profilePicture) {
    const response = h.response({
      status: "fail",
      message: "Gagal menyimpan gambar. Mohon pilih gambar terlebih dahulu",
    });
    response.code(400);
    return response;
  }

  // Memeriksa tipe gambar
  const mimeType = profilePicture.hapi.headers["content-type"];
  if (
    mimeType !== "image/jpeg" &&
    mimeType !== "image/png" &&
    mimeType !== "image/jpg"
  ) {
    const response = h.response({
      status: "fail",
      message: "Gagal menyimpan gambar. Format gambar harus jpg atau png",
    });
    response.code(400);
    return response;
  }

  // Membuat nama file unik untuk gambar
  const fileName = `${userId}.${mimeType.split("/")[1]}`;

  // Mendapatkan file gambar yang diunggah
  const file = profilePicture._data;

  // Menyimpan gambar ke Google Cloud Storage
  const fileUpload = bucket.file(fileName);

  // Memasukkan file gambar ke dalam bucket Google Cloud Storage
  await fileUpload.save(file, {
    contentType: mimeType,
    public: true, // Menjadikan file dapat diakses secara publik
  });

  // Mendapatkan URL file yang dapat diakses
  const imageUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;

  // Menyimpan URL gambar ke pengguna (misalnya di dalam array `users`)
  const user = users.find((user) => user.userId === userId);
  if (user) {
    user.profilePicture = imageUrl; // Menyimpan URL gambar ke profil pengguna
  } else {
    const response = h.response({
      status: "fail",
      message: "User tidak ditemukan",
    });
    response.code(404);
    return response;
  }

  // Response sukses dengan URL gambar
  const response = h.response({
    status: "success",
    message: "Gambar berhasil diupload",
    data: {
      userId,
      profilePictureUrl: imageUrl, // Menampilkan URL gambar untuk frontend
    },
  });
  response.code(201);
  return response;
};

module.exports = {
  saveRegisterInfoHandler,
  saveUserProfilePictureHandler,
  getProfileByIdHandler,
  editProfileByIdHandler,
};
