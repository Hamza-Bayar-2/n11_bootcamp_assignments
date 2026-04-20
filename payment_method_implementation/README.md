# Ödeme Sistemi - Clean Architecture & Strategy Pattern Uygulaması

Bu proje, Spring Boot ekosistemi kullanılarak, SOLID prensipleri ve genişletilebilir tasarım desenleri üzerine inşa edilmiş örnek bir ödeme sistemi entegrasyonudur.

## 🚀 Proje Mimarisi (Clean Architecture)

Proje, sorumlulukların ayrılması (Separation of Concerns) prensibiyle yapılandırılmıştır:
- **api:** İsteklerin karşılandığı controller katmanı.
- **application:** İş mantığının (Service) ve veri transfer nesnelerinin (DTO) bulunduğu katman.
- **domain:** Temel modellerin ve strateji arayüzlerinin (Interface) bulunduğu çekirdek katman.
- **common:** Tüm uygulama genelinde kullanılan ortak yapılar (Result Pattern).

## 🚀 Hızlı Başlangıç

### Projeyi Çalıştırma
Projeyi çalıştırmak için terminalde projenin kök dizinine gidin ve şu komutu çalıştırın:

```bash
mvn spring-boot:run
```

### Örnek API Kullanımı

#### 1. Aktif Ödeme Yöntemlerini Listeleme
Sistemde kayıtlı olan tüm ödeme yöntemlerini görmek için:
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/payments/get-all`

#### 2. Ödeme İşlemi Gerçekleştirme (JSON Body)
Yeni bir ödeme işlemi başlatmak için `POST` isteği gönderin:
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/payments/pay`
- **Body (raw JSON):**
```json
{
  "amount": 2500.50,
  "payerName": "Hamza Bayar",
  "method": "visa",
  "description": "Bootcamp Kayıt Ücreti"
}
```

## Yeni Ödeme Yöntemi Eklemek

1. `IPaymentStrategy` interface'ini eklenen yeni ödeme yöntemine implemente et
2. `@Component("yontem-adi")` annotation'ı class'a ekle
3. Başka hiçbir işleme gerek yok.

## Endpoints
| Method | URL | Açıklama |
|--------|-----|----------|
| POST | /api/payments/pay | Ödeme yap |
| GET | /api/payments/get-all | Aktif yöntemleri listele |

## 🛠️ Kullanılan Tasarım Desenleri ve Yaklaşımlar

### 1. Strategy Pattern (Strateji Deseni)
Ödeme yöntemleri `IPaymentStrategy` arayüzü ile soyutlanmıştır. Her yeni ödeme yöntemi (Visa, PayPal, Crypto vb.) bu arayüzü implemente eder.
- **Avantajı:** **Open-Closed Principle**'a sadık kalınmıştır. Mevcut koda dokunmadan sadece yeni bir sınıf ekleyerek sistem genişletilebilir.

### 2. Spring Native Factory Pattern (Dinamik Fabrika)
Stratejiler manuel olarak `new` anahtar kelimesiyle veya `Class.forName(...).newInstance()` gibi Reflection yöntemleriyle **oluşturulmaz.**
- **İzlenen Yöntem:** Tüm stratejiler `@Component` ile işaretlenerek yaşam döngüsü yönetimi **Spring Boot IoC Container**'ına bırakılmıştır.
- **Artısı:** Stratejiler manuel oluşturulduğunda sadece birer Java objesidir. Spring tarafından yönetildiklerinde ise birer **Spring Bean**'idir. Bu sayede içlerinde başka servisleri, veritabanı bağlantılarını veya konfigürasyonları (`@Value`) sorunsuz kullanabilirler.

### 3. Dinamik Bean Injection (Map Injection)
`PaymentStrategyFactory` içinde `if-else` veya `switch` kalabalığı yoktur.
- **İzlenen Yöntem:** Spring, `IPaymentStrategy` tipindeki tüm bileşenleri otomatik olarak bir `Map<String, IPaymentStrategy>` içine enjekte eder.
- **Artısı:** Yeni bir ödeme yöntemi eklendiğinde Factory sınıfında tek bir satır kod bile değiştirilmez. Spring yeni @Component'i çalışma zamanında otomatik olarak `map` e dahil eder.

### 4. Result Return Pattern (Standart Dönüş Tipi)
Tüm servis ve operasyonlar ortak bir `Result<T>` nesnesi döner.
- **Avantajı:** Proje genelinde hata yönetimi ve başarı durumları standartlaştırılmıştır. Geriye ne döneceği (Success/Failure) her zaman bellidir, bu da kodun okunabilirliğini ve sürdürülebilirliğini artırır.

### 5. DTO (Data Transfer Object) Kullanımı
Dışarıdan gelen veriler `PaymentRequestDto` ile karşılanır. 
- **İzlenen Yöntem:** Modern Java'nın sunduğu `record` yapısı kullanılarak immutable (değiştirilemez) ve hafif veri taşıyıcılar oluşturulmuştur.

### 6. Input Validator (Giriş Doğrulama)
Endpoint'lerden alınan verilerin doğrulanması için `spring-boot-starter-validation` kütüphanesi ve standart validasyon tagları (@NotNull, @NotBlank, @Min vb.) kullanılmıştır. Bu sayede hatalı veriler iş mantığına ulaşmadan API katmanında engellenir.

### 7. Exception Handler (Hata Yönetimi)
Uygulama genelinde oluşan hatalar merkezi bir noktadan yönetilir:
- **Validasyon Hataları:** Yakalanan validasyon hataları anlamlı mesajlarla kullanıcıya sunulur.
- **Global Exception:** Beklenmedik veya genel hatalar için oluşturulan metod ile tüm uygulama içi hatalar yakalanarak standart bir geri bildirim formatında kullanıcıya dönülür.

## Notes
`PaymentRequest` entity'si ileride DB entegrasyonu için hazır tutulmuştur.
Şu an aktif kullanımda değildir.

## 📋 Özet
Bu projede izlenen yol, nesne üretim sorumluluğunu yazılımcıdan alıp framework'e (Spring Boot) devretmektir. Bu sayede bağımlılık yönetimi kolaylaşmış, kodun test edilebilirliği artmış ve SOLID prensiplerine uygun bir altyapı oluşturulmuştur.

