const getMajor = (request, h) => {
  const data = {
    error: false,
    message: "Data fetched successfully",
    data: [
      {
        title: "Aktuari",
        description:
          "Aktuari adalah ilmu yang menggabungkan statistik, matematika, dan keuangan untuk mengukur dan mengelola risiko dan ketidakpastian...",
        skillsRequired: [
          "Kemampuan Matematika Tinggi",
          "Kemampuan Analisis dan Pemecahan Masalah",
          "Keterampilan Teknologi",
          "Komunikasi",
        ],
        universities: [
          "Universitas Gadjah Mada (UGM)",
          "Universitas Airlangga (UNAIR)",
          "Universitas Sebelas Maret (UNS)",
          "Universitas Padjadjaran (UNPAD)",
          "Universitas Islam Indonesia (UII)",
          "Universitas Trisakti (UT)",
          "Universitas Kristen Satya Wacana (UKSW)",
          "Universitas Negeri Yogyakarta (UNY)",
          "Universitas Negeri Jakarta (UNJ)",
          "Universitas Negeri Malang (UM)",
        ],
        careerProspects: [
          {
            careerName: "Aktuaris Asuransi",
            description:
              "Menghitung dan memodelkan risiko yang dihadapi oleh perusahaan asuransi, termasuk risiko kesehatan, jiwa, dan kerugian finansial.",
            salaryRange: "IDR 8.000.000 - IDR 20.000.000/bulan",
          },
          {
            careerName: "Analis Risiko Keuangan",
            description:
              "Menganalisis data keuangan dan menilai potensi risiko di pasar saham atau pasar investasi lainnya.",
            salaryRange: "IDR 7.000.000 - IDR 15.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Peluang Karir Aktuari di Indonesia",
            url: "https://www.jobstreet.co.id/en/career-resources",
          },
          {
            title: "Tips Memilih Karir Aktuari",
            url: "https://www.careers.govt.nz/job-facts/actuary/",
          },
        ],
        tips: "Jika Anda memiliki kemampuan matematika yang sangat baik, menikmati tantangan analisis data, dan tertarik dalam dunia keuangan, jurusan aktuari akan sangat cocok.",
      },
      {
        title: "Farmasi",
        description:
          "Farmasi adalah bidang studi yang berfokus pada ilmu-ilmu yang terkait dengan obat-obatan...",
        skillsRequired: [
          "Pengetahuan Ilmu Kimia dan Biologi",
          "Keterampilan Analisis",
          "Komunikasi yang Baik",
          "Pemahaman Regulasi",
        ],
        universities: [
          "Universitas Gadjah Mada (UGM)",
          "Universitas Airlangga (UNAIR)",
          "Universitas Padjadjaran (UNPAD)",
          "Universitas Andalas (UNAND)",
          "Universitas Brawijaya (UB)",
        ],
        careerProspects: [
          {
            careerName: "Apoteker Rumah Sakit",
            description:
              "Menyediakan informasi mengenai penggunaan obat, memastikan resep yang diberikan sesuai dengan kebutuhan pasien.",
            salaryRange: "IDR 7.000.000 - IDR 15.000.000/bulan",
          },
          {
            careerName: "Peneliti Farmasi",
            description:
              "Bekerja di perusahaan farmasi atau lembaga riset untuk mengembangkan obat baru.",
            salaryRange: "IDR 6.000.000 - IDR 12.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Karir di Dunia Farmasi",
            url: "https://www.careerexplorer.com/careers/pharmacist/",
          },
          {
            title: "Tips dan Sumber Belajar Farmasi",
            url: "https://www.pharmacytimes.com/",
          },
        ],
        tips: "Jika Anda memiliki ketertarikan terhadap bidang medis dan kimia serta ingin membantu orang untuk mendapatkan perawatan yang lebih baik, farmasi adalah pilihan yang tepat.",
      },
      {
        title: "Ilmu Komputer",
        description:
          "Ilmu komputer adalah program studi yang mempelajari bagaimana cara membuat dan mengelola perangkat lunak (software), perangkat keras (hardware), serta sistem komputer secara keseluruhan...",
        skillsRequired: [
          "Pemrograman: Menguasai bahasa pemrograman seperti Python, Java, C++, dan lainnya.",
          "Kemampuan Problem-Solving: Kemampuan untuk mengidentifikasi masalah dan menemukan solusi berbasis komputer.",
          "Pemahaman Algoritma dan Struktur Data: Keterampilan mendalam dalam cara data diatur dan diproses.",
          "Keterampilan Analisis Data dan Kecerdasan Buatan (AI): Memahami penggunaan data untuk pengambilan keputusan atau pengembangan aplikasi yang cerdas.",
        ],
        universities: [
          "Universitas Indonesia (UI)",
          "Universitas Gadjah Mada (UGM)",
          "Universitas Airlangga (UNAIR)",
          "Universitas Diponegoro (UNDIP)",
          "Universitas Brawijaya (UB)",
          "Universitas Telkom (Telkom University)",
          "Universitas Padjadjaran (UNPAD)",
          "Universitas Kristen Satya Wacana (UKSW)",
          "Universitas Negeri Yogyakarta (UNY)",
          "Universitas Pendidikan Indonesia (UPI)",
          "Universitas Teknologi Yogyakarta (UTY)",
          "Universitas Negeri Surabaya (UNESA)",
        ],
        careerProspects: [
          {
            careerName: "Software Developer",
            description:
              "Mengembangkan aplikasi perangkat lunak yang dapat memenuhi kebutuhan pengguna.",
            salaryRange: "IDR 8.000.000 - IDR 20.000.000/bulan",
          },
          {
            careerName: "Data Scientist",
            description:
              "Mengolah dan menganalisis data besar untuk mendukung keputusan bisnis.",
            salaryRange: "IDR 10.000.000 - IDR 25.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Karir di Dunia IT",
            url: "https://www.cio.com/article/",
          },
          {
            title: "Sumber Belajar Pemrograman dan Ilmu Komputer",
            url: "https://www.freecodecamp.org/",
          },
        ],
        tips: "Jika Anda menyukai tantangan dalam menyelesaikan masalah teknis dan tertarik dengan dunia teknologi serta perangkat lunak, ilmu komputer bisa menjadi pilihan yang sangat tepat.",
      },
      {
        title: "Ilmu Kedokteran",
        description:
          "Ilmu kedokteran adalah bidang studi yang mempersiapkan mahasiswa untuk menjadi dokter yang kompeten dalam mendiagnosis, merawat, dan mengobati pasien...",
        skillsRequired: [
          "Pengetahuan Ilmu Kedokteran: Memahami anatomi tubuh manusia, patologi, dan diagnosis medis.",
          "Keterampilan Klinis: Kemampuan untuk mendiagnosis dan merawat pasien.",
          "Keterampilan Komunikasi: Menyampaikan diagnosis dan perawatan kepada pasien dan keluarga mereka.",
          "Ketahanan Fisik dan Mental: Bekerja dalam lingkungan penuh tekanan dan dengan pasien yang membutuhkan perhatian medis.",
        ],
        universities: [
          "Universitas Indonesia (UI)",
          "Universitas Gadjah Mada (UGM)",
          "Universitas Airlangga (UNAIR)",
          "Universitas Padjadjaran (UNPAD)",
          "Universitas Sumatera Utara (USU)",
          "Universitas Hasanuddin (UNHAS)",
          "Universitas Sriwijaya (UNSRI)",
          "Universitas Diponegoro (UNDIP)",
          "Universitas Kristen Satya Wacana (UKSW)",
          "Universitas Tanjungpura (UNTAN)",
          "Universitas Lambung Mangkurat (ULM)",
          "Universitas Negeri Yogyakarta (UNY)",
          "Universitas Gajah Mada (UGM)",
        ],
        careerProspects: [
          {
            careerName: "Dokter Umum",
            description:
              "Menangani berbagai masalah kesehatan sehari-hari dan merujuk pasien ke spesialis jika diperlukan.",
            salaryRange: "IDR 10.000.000 - IDR 25.000.000/bulan",
          },
          {
            careerName: "Dokter Spesialis",
            description:
              "Spesialis dalam bidang medis tertentu, seperti bedah, kardiologi, atau dermatologi.",
            salaryRange: "IDR 20.000.000 - IDR 50.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Karir di Kedokteran",
            url: "https://www.aamc.org/",
          },
          {
            title: "Sumber Belajar Kedokteran",
            url: "https://www.medicalschoolhq.net/",
          },
        ],
        tips: "Jika Anda memiliki ketertarikan yang mendalam terhadap kesehatan, pengobatan, dan membantu orang untuk sembuh, jurusan kedokteran adalah pilihan yang baik.",
      },
      {
        title: "Keperawatan",
        description:
          "Keperawatan adalah program studi yang mempersiapkan mahasiswa untuk menjadi perawat profesional yang memiliki keterampilan dalam merawat dan membantu pasien di rumah sakit, puskesmas, atau lingkungan lainnya. Keperawatan menggabungkan ilmu kesehatan, psikologi, dan keterampilan klinis untuk memberikan perawatan yang optimal kepada pasien.",
        skillsRequired: [
          "Kemampuan Klinis: Pengetahuan dan keterampilan dalam melakukan tindakan medis dasar, termasuk pengobatan dan perawatan pasien.",
          "Kemampuan Komunikasi: Keterampilan dalam berkomunikasi dengan pasien, keluarga pasien, dan tim medis lainnya.",
          "Empati dan Ketahanan Mental: Kemampuan untuk merawat pasien dalam kondisi fisik dan emosional yang menantang.",
          "Keterampilan Manajerial: Dalam pengelolaan waktu dan sumber daya di lingkungan rumah sakit atau klinik.",
        ],
        universities: [
          "Universitas Indonesia (UI)",
          "Universitas Gadjah Mada (UGM)",
          "Universitas Airlangga (UNAIR)",
          "Universitas Padjadjaran (UNPAD)",
        ],
        careerProspects: [
          {
            careerName: "Perawat Rumah Sakit",
            description:
              "Menangani pasien yang membutuhkan perawatan medis, memberikan obat-obatan, serta mendampingi proses pemulihan.",
            salaryRange: "IDR 5.000.000 - IDR 10.000.000/bulan",
          },
          {
            careerName: "Perawat Spesialis",
            description:
              "Perawat dengan keahlian khusus di bidang tertentu, seperti perawatan intensif, anak, atau geriatri.",
            salaryRange: "IDR 7.000.000 - IDR 15.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Karir Perawat Profesional",
            url: "https://www.nursingworld.org/",
          },
          {
            title: "Sumber Belajar Keperawatan",
            url: "https://www.careeronestop.org/",
          },
        ],
        tips: "Jika Anda memiliki minat untuk langsung membantu orang, berempati dengan pasien, dan bekerja dalam dunia medis tanpa harus menjadi dokter, keperawatan adalah pilihan yang sangat baik.",
      },
      {
        title: "Matematika",
        description:
          "Program studi matematika fokus pada pemahaman teori dan aplikasi matematika di berbagai bidang, mulai dari statistik, aljabar, kalkulus, hingga topologi dan analisis matematis.",
        skillsRequired: [
          "Pemikiran Logis dan Analitis: Kemampuan untuk berpikir abstrak dan menyusun solusi matematika untuk masalah yang kompleks.",
          "Keterampilan Pemrograman: Penguasaan bahasa pemrograman seperti Python atau R untuk memproses data dan menjalankan simulasi.",
        ],
        universities: [
          "Institut Teknologi Bandung (ITB)",
          "Universitas Indonesia (UI)",
          "Universitas Gadjah Mada (UGM)",
        ],
        careerProspects: [
          {
            careerName: "Analis Data",
            description:
              "Menganalisis data besar untuk menemukan pola dan memberikan wawasan bagi perusahaan.",
            salaryRange: "IDR 8.000.000 - IDR 20.000.000/bulan",
          },
          {
            careerName: "Matematikawan Terapan",
            description:
              "Menggunakan prinsip matematika untuk memecahkan masalah praktis di bidang teknologi atau keuangan.",
            salaryRange: "IDR 10.000.000 - IDR 25.000.000/bulan",
          },
        ],
        webLinks: [
          { title: "Karir Matematikawan", url: "https://www.ams.org/" },
          {
            title: "Sumber Belajar Matematika",
            url: "https://www.khanacademy.org/",
          },
        ],
        tips: "Jika Anda tertarik pada angka, logika, dan ingin mengembangkan kemampuan untuk memecahkan masalah kompleks, matematika adalah pilihan yang ideal.",
      },
      {
        title: "Teknik Elektro",
        description:
          "Teknik elektro adalah disiplin ilmu yang berkaitan dengan perancangan, pengembangan, dan pengoperasian sistem elektronik dan kelistrikan.",
        skillsRequired: [
          "Pemahaman tentang Listrik dan Elektronika: Mahasiswa perlu memahami cara kerja listrik, rangkaian elektronik, serta teknologi sistem kelistrikan.",
          "Kemampuan Pemrograman: Menguasai bahasa pemrograman untuk mengendalikan perangkat keras atau mengembangkan software untuk sistem kelistrikan dan elektronik.",
        ],
        universities: [
          "Institut Teknologi Bandung (ITB)",
          "Universitas Indonesia (UI)",
          "Universitas Gadjah Mada (UGM)",
        ],
        careerProspects: [
          {
            careerName: "Engineer Telekomunikasi",
            description:
              "Merancang dan mengelola sistem komunikasi berbasis teknologi elektronik.",
            salaryRange: "IDR 8.000.000 - IDR 15.000.000/bulan",
          },
          {
            careerName: "Power Systems Engineer",
            description:
              "Merancang dan menganalisis sistem kelistrikan untuk memenuhi kebutuhan energi di berbagai sektor.",
            salaryRange: "IDR 10.000.000 - IDR 20.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Karir di Teknik Elektro",
            url: "https://www.engineering.com/",
          },
          {
            title: "Sumber Belajar Teknik Elektro",
            url: "https://www.edx.org/learn/electrical-engineering",
          },
        ],
        tips: "Jika Anda tertarik dengan sistem kelistrikan dan elektronik serta ingin bekerja di industri teknologi, teknik elektro adalah pilihan yang tepat.",
      },
      {
        title: "Teknik Kelautan",
        description:
          "Teknik kelautan adalah cabang teknik yang berfokus pada desain, konstruksi, dan pemeliharaan infrastruktur yang berhubungan dengan laut. Ini mencakup proyek-proyek besar seperti pelabuhan, pembangkit listrik tenaga laut, eksplorasi sumber daya alam di laut, serta penelitian dan pengelolaan lingkungan laut.",
        skillsRequired: [
          "Pemahaman tentang Fisika dan Rekayasa Struktur Laut: Mempelajari bagaimana struktur dapat bertahan dalam kondisi ekstrem yang ada di bawah air.",
          "Kemampuan Desain: Merancang infrastruktur dan peralatan yang dapat berfungsi di bawah air atau di lingkungan maritim.",
          "Kemampuan Analisis Lingkungan: Memahami ekosistem laut dan dampak dari rekayasa pada lingkungan.",
          "Keterampilan dalam Pengelolaan Proyek: Merencanakan dan mengelola proyek kelautan besar dengan efektif.",
        ],
        universities: [
          "Institut Teknologi Bandung (ITB)",
          "Universitas Hasanuddin (UNHAS)",
          "Universitas Diponegoro (UNDIP)",
          "Universitas Gadjah Mada (UGM)",
          "Universitas Sam Ratulangi (UNSRAT)",
          "Universitas Sriwijaya (UNSRI)",
          "Universitas Pattimura (UNPATTI)",
          "Universitas Borneo Tarakan (UBT)",
          "Universitas Maluku (UNMAL)",
          "Universitas Negeri Makassar (UNM)",
          "Universitas Halu Oleo (UHO)",
        ],
        careerProspects: [
          {
            careerName: "Marine Engineer",
            description:
              "Merancang dan mengembangkan peralatan serta sistem di sektor kelautan.",
            salaryRange: "IDR 8.000.000 - IDR 20.000.000/bulan",
          },
          {
            careerName: "Offshore Engineer",
            description:
              "Mengembangkan infrastruktur dan teknologi untuk eksplorasi dan produksi energi laut, seperti pengeboran minyak lepas pantai.",
            salaryRange: "IDR 12.000.000 - IDR 25.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Karir di Teknik Kelautan",
            url: "https://www.oilcareers.com/",
          },
          {
            title: "Sumber Belajar Teknik Kelautan",
            url: "https://www.coursera.org/learn/marine-engineering",
          },
        ],
        tips: "Jika Anda tertarik dengan dunia kelautan, teknologi, dan ingin berkontribusi pada eksplorasi serta perlindungan sumber daya laut, teknik kelautan adalah pilihan yang menarik.",
      },
      {
        title: "Teknik Sipil",
        description:
          "Teknik sipil adalah bidang studi yang berfokus pada perencanaan, desain, konstruksi, dan pemeliharaan infrastruktur publik seperti jalan raya, jembatan, gedung, bendungan, sistem saluran air, dan fasilitas lainnya.",
        skillsRequired: [
          "Keterampilan Desain: Kemampuan untuk merancang struktur dan infrastruktur yang aman, fungsional, dan efisien.",
          "Pengetahuan tentang Bahan Bangunan: Memahami berbagai bahan dan teknik konstruksi.",
          "Analisis dan Pemecahan Masalah: Menggunakan teknik analisis struktural untuk memastikan bangunan dan infrastruktur dapat menahan beban dan kondisi alam.",
          "Keterampilan Manajerial dan Pengelolaan Proyek: Memahami cara mengelola proyek konstruksi besar.",
        ],
        universities: [
          "Institut Teknologi Bandung (ITB)",
          "Universitas Indonesia (UI)",
          "Universitas Gadjah Mada (UGM)",
          "Universitas Brawijaya (UB)",
          "Universitas Diponegoro (UNDIP)",
          "Universitas Hasanuddin (UNHAS)",
        ],
        careerProspects: [
          {
            careerName: "Insinyur Struktur",
            description:
              "Merancang struktur bangunan dan infrastruktur, seperti gedung, jembatan, atau jalan.",
            salaryRange: "IDR 7.000.000 - IDR 15.000.000/bulan",
          },
          {
            careerName: "Manajer Konstruksi",
            description:
              "Mengawasi proyek konstruksi dari awal hingga selesai, memastikan proyek sesuai jadwal dan standar keselamatan.",
            salaryRange: "IDR 10.000.000 - IDR 25.000.000/bulan",
          },
        ],
        webLinks: [
          { title: "Karir di Teknik Sipil", url: "https://www.asce.org/" },
          {
            title: "Sumber Belajar Teknik Sipil",
            url: "https://www.coursera.org/courses?query=civil%20engineering",
          },
        ],
        tips: "Jika Anda tertarik pada perencanaan dan pembangunan infrastruktur yang dapat berdampak langsung pada kehidupan banyak orang, teknik sipil adalah pilihan yang tepat.",
      },
      {
        title: "Teknologi Pangan",
        description:
          "Teknologi pangan adalah bidang studi yang mempelajari proses pengolahan, pengawetan, distribusi, dan konsumsi pangan dengan fokus pada penerapan ilmu dan teknologi untuk meningkatkan kualitas dan keamanan produk makanan.",
        skillsRequired: [
          "Pemahaman Ilmu Pangan: Mengetahui cara kerja bahan pangan, pengolahan, dan pengemasan.",
          "Teknik Pengolahan Makanan: Menguasai teknik-teknik pengolahan dan pengemasan.",
          "Mikrobiologi dan Keamanan Pangan: Memahami cara mencegah kontaminasi.",
          "Inovasi Produk Pangan: Mengembangkan produk pangan baru.",
        ],
        universities: [
          "Institut Pertanian Bogor (IPB)",
          "Universitas Gadjah Mada (UGM)",
          "Universitas Padjadjaran (UNPAD)",
          "Universitas Brawijaya (UB)",
          "Universitas Hasanuddin (UNHAS)",
        ],
        careerProspects: [
          {
            careerName: "Ahli Teknologi Pangan",
            description:
              "Mengembangkan dan meningkatkan proses pengolahan makanan.",
            salaryRange: "IDR 6.000.000 - IDR 15.000.000/bulan",
          },
          {
            careerName: "Quality Control/Assurance",
            description:
              "Memastikan produk makanan memenuhi standar kualitas dan keamanan.",
            salaryRange: "IDR 5.000.000 - IDR 10.000.000/bulan",
          },
        ],
        webLinks: [
          {
            title: "Karir di Teknologi Pangan",
            url: "https://www.foodprocessing.com/",
          },
          {
            title: "Sumber Belajar Teknologi Pangan",
            url: "https://www.coursera.org/courses?query=food%20technology",
          },
        ],
        tips: "Jika Anda tertarik untuk bekerja di bidang pangan dan produksi makanan yang berkualitas, teknologi pangan adalah pilihan yang baik.",
      },
    ],
  };
  return h.response(data).code(200);
};

module.exports = { getMajor };
