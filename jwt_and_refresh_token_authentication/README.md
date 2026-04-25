# Jwt ve Refresh Token Authentication Sistemi

Bu proje; Spring Boot 3, Spring Security 6 ve JWT (JSON Web Token) kullanarak geliştirilmiş, güvenli, stateless ve modern bir kimlik doğrulama sistemidir. Proje, temiz kod prensipleri (SOLID) ve CQRS mimarisi üzerine inşa edilmiştir.

## 🚀 Öne Çıkan Özellikler

- **Stateless Auth:** JWT ve Refresh Token yapısı ile sunucu tarafında session saklanmaz.
- **Güvenli Cookie Yönetimi:** Token'lar `HttpOnly`, `Secure` ve `SameSite=Strict` özellikli cookie'lerde saklanarak XSS ve CSRF saldırılarına karşı korunur.
- **Token Rotation:** Refresh token her kullanıldığında yenilenerek (rotation) ele geçirilme riskini minimize eder.
- **Rol Tabanlı Yetkilendirme:** `ADMIN`, `USER` ve `MODERATOR` rolleri ile granular erişim kontrolü sağlanır.
- **Otomatik Login:** Kayıt (Register) işlemi sonrası kullanıcı login yapmasına gerek kalmadan otomatik olarak sisteme dahil edilir.

## 🛠️ Mimari Yapı ve Tasarım Desenleri

### 1. CQRS (Command Query Responsibility Segregation)
İş mantığı, yazma (Command) ve okuma (Query) işlemleri olarak birbirinden ayrılmıştır. Tüm iş akışı handler sınıfları üzerinden yönetilir.

### 2. Result Pattern
Uygulama katmanındaki tüm handlerlar hata yönetimi için Exception fırlatmak yerine `Result<T>` nesnesi döner. Bu sayede akış kontrolü (fail-fast) daha öngörülebilir ve temizdir.

### 3. Composite Key Strategy
`UserRole` entity'si, `user_id` ve `role_type` alanlarının birleşimiyle oluşan bileşik anahtar (`@IdClass`) kullanır.

## 🚀 Hızlı Başlama

### 1. Veritabanını Başlatma (Docker)
PostgreSQL veritabanını uygulamadaki ayarlara (`application.properties`) uygun olarak ayağa kaldırmak için:

```bash
docker run --name bootcamp-postgres -e POSTGRES_DB=bootcamp -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=1234 -p 5432:5432 -d postgres
```

### 2. Uygulamayı Çalıştırma
```bash
mvn spring-boot:run
```

## 📋 API Endpoints

### Kimlik Doğrulama (`/api/auth`)
| Method | URL | Açıklama |
|--------|-----|----------|
| POST | `/api/auth/register` | Yeni kullanıcı kaydı ve otomatik login |
| POST | `/api/auth/login` | Giriş yap ve token cookie'lerini al |
| POST | `/api/auth/logout` | Session'ı sonlandır ve cookie'leri temizle |
| POST | `/api/auth/refresh` | Mevcut refresh token ile yeni JWT al (Rotation) |

### Kullanıcı Yönetimi (`/api/users`)
| Method | URL | Yetki | Açıklama |
|--------|-----|-------|----------|
| GET | `/api/users` | `ADMIN` | Tüm kullanıcıları listele |
| DELETE | `/api/users` | `ADMIN` | Tüm kullanıcıları sil |
| GET | `/api/users/me` | `AUTH` | Aktif kullanıcı bilgilerini getir |
| DELETE | `/api/users/{email}` | `OWNER/ADMIN` | Belirtilen kullanıcıyı sil |

## 🛠️ Kullanılan Teknolojiler
- **Java 17 / Spring Boot 3.x**
- **Spring Security 6**
- **JPA / Hibernate / PostgreSQL**
- **JJWT (Java JWT Library)**
- **Lombok / Jakarta Validation**

---
_Bu proje bir eğitim bootcamp ödevi kapsamında "Clean Code" prensipleri doğrultusunda hazırlanmıştır._
