#  ToDo List Uygulaması

Kullanıcıların kişisel görevlerini (ToDo) yönetebileceği, kimlik doğrulama ve yetkilendirme destekli bir web uygulamasıdır.

---

##  Kullanılan Teknolojiler

| Katman       | Teknoloji           |
|--------------|---------------------|
| Backend      | Java 17 - Spring Boot 2.0.0 |
| Veritabanı   | MySQL |
| Framework    | Spring MVC, Spring Security, Spring Data JPA |
| Diğer        | JWT, Maven, Cloudinary (resim için), Bean Validation |

---

##  Projeyi Çalıştırma
### 1. Projeyi klonla
     git clone https://github.com/kullanici-adi/proje-adi.git
     cd proje-adi

### 2. Maven bağımlılıklarını indir (IDE bunu otomatik yapabilir)
    ./mvnw clean install

### 3. Uygulamayı başlat
    ./mvnw spring-boot:run

### 4. application.properties Ayarları
![Ekran Resmi 2025-07-18 10.05.49.png](images/Ekran%20Resmi%202025-07-18%2010.05.49.png)


##  API Endpointleri
## User
<details>
<summary><strong>1. POST `http://localhost:8080/user/register`</strong></summary>

**Açıklama**: Sisteme yeni bir kullanıcı kaydı yapılır.


- **Request Body**:
    ```json
    {
  "email": "beyza@gmail.com",
  "password": "123",
  "firstName": "Beyza",
  "lastName": "Yücel",
  "tel": "1234567890"
    }
- **Response Body**:
    ```json
    200 OK  

- **Errors**:![Ekran Resmi 2025-07-18 09.15.11.png](images/Ekran%20Resmi%202025-07-18%2009.15.11.png)
</details>

<details>
<summary><strong> 2. POST `http://localhost:8080/user/login' </strong></summary>

**Açıklama**: Kullanıcı sisteme giriş yapar.


- **Request Body**:
    ```json
    {
    "email":"beyzanuryucel66@gmail.com",
    "password": "123"
    }
- **Response Body**:
    ```json
    {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"
    }

- **Errors**:![Ekran Resmi 2025-07-18 09.26.03.png](images/Ekran%20Resmi%202025-07-18%2009.26.03.png)

</details>

<details>
<summary><strong>3. POST `http://localhost:8080/forgotten-password-send-otp`</strong></summary>

**Açıklama**: Kullanıcı, şifresini unuttuğunda e-posta adresine OTP kodu gönderilir.

- **Request Body**:
    ```json
    {
    "email": "beyza@gmail.com"
    }
- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 09.27.19.png](images/Ekran%20Resmi%202025-07-18%2009.27.19.png)

</details>

<details>
<summary><strong> 4. POST `http://localhost:8080/forgotten-password-verify' </strong></summary>

**Açıklama**: Kullanıcı, mailine gelen OTP kodunu girerek doğrulama yapar.

- **Request Body**:
    ```json
    
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"
    
    {
    "email": "beyza@gmail.com",
    "otp":"969558"
    }
  
- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 09.34.05.png](images/Ekran%20Resmi%202025-07-18%2009.34.05.png)

</details>

<details>
<summary><strong> 5. POST `http://localhost:8080/forgotten-password-reset` </strong></summary>


**Açıklama**: Kullanıcı, yeni şifresini girer.

- **Request Body**:
    ```json
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

    {
    "email":"beyzanuryucel66@gmail.com",
    "newPassword":"123",
    "newPasswordAgain":"123"
    }

- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 09.35.42.png](images/Ekran%20Resmi%202025-07-18%2009.35.42.png)

</details>

<details>
<summary><strong> 6. PATCH `http://localhost:8080/user/update/me` </strong></summary>


**Açıklama**: Kullanıcı, bilgilerini günceller.

- **Request Body**:
    ```json
    
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"
    
    {
    "tel":"1234567890"
    }

- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 09.40.33.png](images/Ekran%20Resmi%202025-07-18%2009.40.33.png)

</details>

<details>
<summary><strong> 7. POST `http://localhost:8080/user/upload-images` </strong></summary>

**Açıklama**: Kullanıcı, sisteme görüntü yükler.


- **Request Body**:
  ```json
    
  token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"
![Ekran Resmi 2025-07-17 17.28.58.png](images/Ekran%20Resmi%202025-07-17%2017.28.58.png)

- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 09.43.40.png](images/Ekran%20Resmi%202025-07-18%2009.43.40.png)

</details>

<details>
<summary><strong> 8. GET `http://localhost:8080/user/my-images` </strong></summary>


**Açıklama**: Kullanıcı, resimlerini görüntüler.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    200 OK
</details>

<details>
<summary><strong> 9. GET `http://localhost:8080/user/current-user` </strong></summary>


**Açıklama**: Kullanıcı, kendi bilgilerini görüntüler.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"


- **Response Body**:
    ```json
    {
    "firstName": "Beyzanur",
    "lastName": "Yücel",
    "email": "beyzanuryucel66@gmail.com",
    "tasks": [
        {
            "title": "Sql",
            "createdDate": "2012-12-12",
            "important": "Düşük",
            "status": "IN_PROGRESS",
            "description": "Sql çalış",
            "id": 37
        },
        {
            "title": "Çamaşır",
            "createdDate": "2012-12-12",
            "important": "Düşük",
            "status": "CREATED",
            "description": "Çamaşırları yıka",
            "id": 64
        },
        {
            "title": "Bilgisayar",
            "createdDate": "2012-12-12",
            "important": "Düşük",
            "status": "CREATED",
            "description": "Bilgisayar bak",
            "id": 65
        }
    ],
    "tel": "1234567890",
    "userImages": [
        {
            "id": 1,
            "imageUrl": "http://res.cloudinary.com/dne5k7rqw/image/upload/v1752751502/jkfagj1o4tak6hoegcea.jpg",
            "date": "2025-07-16T11:19:40.804+00:00"
        },
        {
            "id": 2,
            "imageUrl": "http://res.cloudinary.com/dne5k7rqw/image/upload/v1752751503/yy47776zhutjz16e3bpl.png",
            "date": "2025-07-16T11:19:42.288+00:00"
        }
    ]

</details>

<details>
<summary><strong> 10. GET `http://localhost:8080/user/my-images` </strong></summary>


**Açıklama**: Kullanıcı, resimlerini görüntüler.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
  {
    "id": 9,
    "imageUrl": "http://res.cloudinary.com/dne5k7rqw/image/upload/v1752751502/jkfagj1o4tak6hoegcea.jpg", 
    "date": "2025-07-17T11:25:02.695+00:00"
  }, 
  {
     "id": 10, 
     "imageUrl": "http://res.cloudinary.com/dne5k7rqw/image/upload/v1752751503/yy47776zhutjz16e3bpl.png", 
     "date": "2025-07-17T11:25:04.129+00:00"
  }
</details>

## Task

<details>
<summary><strong> 1. GET `http://localhost:8080/task/list` </strong></summary>


**Açıklama**: Kullanıcı, görevlerini görüntüler.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    {
    "motivationDto": {
        "auth": "Mark Manson",
        "quote": "The less you talk about your shame, the more of it you have."
    },
    "tasks": [
        {
            "title": "Tatil",
            "createdDate": "2019-08-01",
            "important": "Düşük",
            "status": "CREATED",
            "description": "Tatil yeri bak",
            "id": 37
        },
        {
            "title": "Çamaşır",
            "createdDate": "2012-12-12",
            "important": "Düşük",
            "status": "CREATED",
            "description": "Çamaşırları yıka",
            "id": 64
        },
        {
            "title": "Bilgisayar",
            "createdDate": "2012-12-12",
            "important": "Düşük",
            "status": "CREATED",
            "description": "Bilgisayar bak",
            "id": 65
        }
    ]
</details>

<details>
<summary><strong> 2. GET `http://localhost:8080/task/title/{title}` </strong></summary>


**Açıklama**: Kullanıcı, görevlerini titleye göre arar.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    {
    "title": "Tatil",
    "createdDate": "2019-08-01",
    "important": "Düşük",
    "status": "CREATED",
    "description": "Tatil yeri bak",
    "id": 37
  }
</details>

<details>
<summary><strong> 3. POST `http://localhost:8080/task/create` </strong></summary>


**Açıklama**: Kullanıcı, yeni görev oluşturur.

- **Request Body**:
    ```json
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

    {
    "title":"Bilgisayar",
    "createdDate":"2012-12-12",
    "important":"Düşük",
    "description":"Bilgisayar bak"
    }

- **Response Body**:
    ```json
    {
    "title": "Bilgisayar",
    "createdDate": "2012-12-12",
    "important": "Düşük",
    "status": "CREATED",
    "description": "Bilgisayar bak",
    "id": 65
}

- **Errors**:![Ekran Resmi 2025-07-18 09.48.38.png](images/Ekran%20Resmi%202025-07-18%2009.48.38.png)

</details>

<details>
<summary><strong> 4. GET `http://localhost:8080/task/status/{status}` </strong></summary>


**Açıklama**: Kullanıcı, görevlerini statusa göre arar.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    [
    {
        "title": "Tatil",
        "createdDate": "2019-08-01",
        "important": "Düşük",
        "status": "CREATED",
        "description": "Tatil yeri bak",
        "id": 37
    },
    {
        "title": "Çamaşır",
        "createdDate": "2012-12-12",
        "important": "Düşük",
        "status": "CREATED",
        "description": "Çamaşırları yıka",
        "id": 64
    },
    {
        "title": "Bilgisayar",
        "createdDate": "2012-12-12",
        "important": "Düşük",
        "status": "CREATED",
        "description": "Bilgisayar bak",
        "id": 65
    }
  ]
</details>

<details>
<summary><strong> 5. GET `http://localhost:8080/task/sortDate` </strong></summary>


**Açıklama**: Kullanıcı, görevlerini tarihe göre sıralar.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    [
    {
        "title": "Çamaşır",
        "createdDate": "2012-12-12",
        "important": "Düşük",
        "status": "CREATED",
        "description": "Çamaşırları yıka",
        "id": 64
    },
    {
        "title": "Tatil",
        "createdDate": "2019-08-01",
        "important": "Düşük",
        "status": "CREATED",
        "description": "Tatil yeri bak",
        "id": 37
    }
  ]
</details>

<details>
<summary><strong> 6. DELETE `http://localhost:8080/task/delete/36` </strong></summary>


**Açıklama**: Kullanıcı, görevini siler.


- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 09.51.37.png](images/Ekran%20Resmi%202025-07-18%2009.51.37.png)

</details>

<details>
<summary><strong> 7. PUT `http://localhost:8080/task/update/37` </strong></summary>


**Açıklama**: Kullanıcı, görevini günceller.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"
    
  {
        "title": "Sql",
        "createdDate": "2012-12-12",
        "important": "Düşük",
        "description": "Sql çalış"
    }

- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 09.53.17.png](images/Ekran%20Resmi%202025-07-18%2009.53.17.png)

</details>

<details>
<summary><strong> 8. GET `http://localhost:8080/task/get/{id}` </strong></summary>


**Açıklama**: Kullanıcı, id'ye göre görevini getirir.

- **Request Body**:
    ```json
  
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    200 OK
</details>

## Admin

<details>
<summary><strong> 1. POST `http://localhost:8080/admin/login` </strong></summary>


**Açıklama**: Admin sisteme giriş yapar.

- **Request Body**:
    ```json
    {
    "email":"admin@gmail.com",
    "password":"123"
    }

- **Response Body**:
    ```json
    {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE3NTI3NTIyNzUsImV4cCI6MTc1Mjc3MDI3NX0.cp3rC7Bujpng3li4AwB2pCXZv1EbX9XIW5zfWYMbA1c"
    }

- **Errors**:![Ekran Resmi 2025-07-18 09.58.01.png](images/Ekran%20Resmi%202025-07-18%2009.58.01.png)

</details>

<details>
<summary><strong> 2. POST  `http://localhost:8080/admin/register` </strong></summary>


**Açıklama**: Admin, sisteme admin ekler.

- **Request Body**:
    ```json
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

    {
    "email": "Ceren@gmail.com",
    "password": "123",
    "firstName": "Ceren",
    "lastName": "Yücel",
    "tel":"0843723764"
    }

- **Response Body**:
    ```json
    200 OK

- **Errors**:![Ekran Resmi 2025-07-18 10.02.39.png](images/Ekran%20Resmi%202025-07-18%2010.02.39.png)

</details>

<details>
<summary><strong> 3. GET  `http://localhost:8080/admin/users` </strong></summary>


**Açıklama**: Admin, sistemde kayıtlı kullanıcı bilgilerini görüntüler.


- **Request Body**:
    ```json
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXl6YW51cnl1Y2VsNjZAZ21haWwuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTI3NTE2NDksImV4cCI6MTc1Mjc2OTY0OX0.EoaROMn85n35XBrTdjoFCCOBDcSIacEbxpyYyfddM8w"

- **Response Body**:
    ```json
    [
    {
        "email": "yazgi@gmail.com",
        "firstName": "yazgı",
        "lastName": "coşkun",
        "tasks": [
            {
                "title": "Bavul",
                "createdDate": "2013-08-01",
                "important": "Yüsek",
                "status": null,
                "description": "Bavul hazırla",
                "id": 33
            },
            {
                "title": "Sql",
                "createdDate": "2012-12-12",
                "important": "Orta",
                "status": "IN_PROGRESS",
                "description": "Sql çalış",
                "id": 34
            }
        ],
        "tel": "5465602332",
        "userImages": [
            {
                "id": 4,
                "imageUrl": "https://res.cloudinary.com/dne5k7rqw/image/upload/v1752670469/vwqmtdecc7af7rjnlboe.jpg",
                "date": "2025-07-16T12:54:30.099+00:00"
            }
        ]
    },
    {
        "email": "beyza@gmail.com",
        "firstName": "Beyzanur",
        "lastName": "Yücel",
        "tasks": [
            {
                "title": "Sql",
                "createdDate": "2012-12-12",
                "important": "Düşük",
                "status": "IN_PROGRESS",
                "description": "Sql çalış",
                "id": 37
            },
            {
                "title": "Çamaşır",
                "createdDate": "2012-12-12",
                "important": "Düşük",
                "status": "CREATED",
                "description": "Çamaşırları yıka",
                "id": 64
            },
            {
                "title": "Bilgisayar",
                "createdDate": "2012-12-12",
                "important": "Düşük",
                "status": "CREATED",
                "description": "Bilgisayar bak",
                "id": 65
            }
        ],
        "tel": "1234567890",
        "userImages": [
            {
                "id": 9,
                "imageUrl": "http://res.cloudinary.com/dne5k7rqw/image/upload/v1752751502/jkfagj1o4tak6hoegcea.jpg",
                "date": "2025-07-17T11:25:02.695+00:00"
            },
            {
                "id": 10,
                "imageUrl": "http://res.cloudinary.com/dne5k7rqw/image/upload/v1752751503/yy47776zhutjz16e3bpl.png",
                "date": "2025-07-17T11:25:04.129+00:00"
            }
        ]
    },
   
    {
        "email": "ada@gmail.com",
        "firstName": "Ada",
        "lastName": "Öz",
        "tasks": [],
        "tel": "1234567890",
        "userImages": []
    }
  ]
</details>