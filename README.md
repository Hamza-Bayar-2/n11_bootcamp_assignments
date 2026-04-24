# n11_bootcamp_assignments

This repository contains the assignments and projects completed during the n11 Backend Bootcamp program.

## 👉 1. Ödev: Yeni Ödeme Yöntemi Entegrasyonu (SOLID Prensipleri ile)
Bir ödeme ekranı uygulamasında çalıştığınızı düşünün. Mevcut sistemde hali hazırda bazı ödeme yöntemleri bulunmaktadır.
Yeni bir ödeme yöntemi sisteme eklenecektir. Bu entegrasyonu gerçekleştirirken aşağıdaki gereksinimleri dikkate almanız beklenmektedir:

- **Proje Klasörü**: [(payment_method_implementation)](payment_method_implementation)

### 📌 Beklentiler
- Mevcut kod yapısını mümkün olduğunca **bozmadan** ilerleyin.
- Yeni ödeme yöntemini **SOLID yazılım prensiplerine uygun** şekilde sisteme entegre edin.
- Özellikle aşağıdaki prensiplere dikkat edin:
  - **Open/Closed Principle (OCP):** Sistemi değiştirmeden genişletebilme
  - **Single Responsibility Principle (SRP):** Sınıfların tek bir sorumluluğu olması
  - (Uygunsa diğer SOLID prensiplerini de uygulayabilirsiniz)
  
### ⚙️ Teknik Gereksinimler
- Uygulamanın basit bir ödeme akışı içermesi yeterlidir.
- En az:
  - 1 adet mevcut ödeme yöntemi (örnek: Kredi Kartı)
  - 1 adet yeni eklenen ödeme yöntemi (örnek: PayPal, Apple Pay vb.)
- Kod yapınız genişlemeye uygun olmalıdır.

### 📤 Teslimat
- Çalışmanızı bir GitHub repository olarak paylaşın. 
- Repository içerisinde: 
  - Çalışan kod 
  - Kısa bir README dosyası (yaklaşımınızı ve tasarım kararlarınızı açıklayan) 

## 👉 2. Ödev: Refresh ve JWT Token ile authentication katmanını kurun

### 📌 Proje Amacı

Bu projede, ders kapsamında işlenen **JWT (JSON Web Token)** konusu ele alınmaktadır.  
Temel hedef, JWT kullanarak bir kimlik doğrulama (authentication) mekanizması oluşturmak ve bu yapıyı daha gelişmiş bir mimari olan **Refresh Token Mimarisi** ile genişletmektir.

Bu proje kapsamında aşağıdaki adımların gerçekleştirilmesi beklenmektedir:

- JWT tabanlı authentication yapısının kurulması  
- Access Token ve Refresh Token kavramlarının uygulanması  
- Mevcut projenin, **Refresh Token mimarisine uygun şekilde yeniden tasarlanması**  
- Token yenileme (refresh) mekanizmasının implement edilmesi  
- Güvenli ve sürdürülebilir bir authentication altyapısının oluşturulması

Bu proje, sadece temel JWT kullanımını değil; aynı zamanda gerçek dünya uygulamalarında kullanılan **Refresh Token mimarisinin mantığını ve uygulanışını kavramayı** amaçlamaktadır.

