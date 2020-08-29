# Android网络安全：加解密:secret:
RSA、DH、AES

### 需求点：

- 利用加密技术，对内容进行加密
- 对称加密
- 非对称加密
- 破解难度最高的DH密码交换方式加密技术

### 加解密技术：

> 加密技术是最常用的安全保密手段，利用技术手段把重要的数据变为乱码(加密)传送，到达目的地后再用相同or不同的手段进行还原(解密)
>
> 注：加密领域中，性能、安全，一直都是相对存在的。一个优秀的加密方案必然要能够平衡两者。

加解密的关键：安全保密、明文转换为密文、在不可信的通道上传输密文、采用相同or不同的手段进行还原为明文

加解密技术分类：

- 哈希函数——SHA256、MD5
- 对称加密——AES、DES
- 非对称加密——RSA、ECC、Elgamal
- 密钥交换——DH、ECDH

### 对称加密与非对称加密：

1. 对称加密算法——AES(Advanced Encryption Standard，高级加密标准)

- 加密：C = E(K,  P),其中P为明文，K为密钥，C为密文
- 解密： P = D(K, C),其中C为密文，K为密钥，P为明文

此算法用同一个密钥进行加解密，用同样的方式还原明文。这样的加密效率高、计算量小、使用方便，但是也会导致双方需要协商密钥、一旦密钥泄漏则加密系统崩塌、密钥更新和维护困难。

   2.非对称加密算法——RSA

RSA是一种公开密钥密码体制。

其可靠性基础：

- 大质数相乘计算很容易
- 对极大整数做因数分解很困难

例如：

- 1111 x 3331 = 3700741
- 3700741 = ? x ?

RSA加密解密是用不同的密钥，采用不同样的方式还原明文。所以公钥可以开放、密钥方便管理、安全性高，但是其性能差、加密时间长，使用起来也相对复杂一点。



### 移动端加解密：密钥交换算法——DH

DH（Diffie-Hellman），一种确保共享Key安全的穿越不安全网络的方法。

- 不需要保证信道安全
- 计算公式可完全公开:PubKey公钥可公开、PriKey私钥不公开、Secret密钥加密用
- 双方不需要知道对方的私钥
- 可协商出一个其他人都不知道的密钥



### CS网络模型:

网络模型设计——C/S3次握手

​              ----ClientHello发送客户端DH公钥--------》

客户端：<-------ServerHello发送服务器DH公钥 ----                服务器

​               ----确认收到，完成握手--------》



