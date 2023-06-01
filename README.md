# LTWeb_RING
An E-Commerce Web App using Java Servlet, JSP.

## Local setup
#### Database setup (MSSQL)
- Right click on `Database` folder
- Choose `Restore Database`
- Click on `Device` tick
- Find the Initialize database file `RING.bak` and click `Restore`

#### Config DBContext
- Change Database Path, User & Pass of your MSSQL in `DBContext.java`
```java
    private final String userID = <your database username>;
    private final String password = <your database password>;
```
#### Config web.xml
- Change to your Email & Pass in `web.xml` and active `third party app` in your google account for the auto mail function
```xml
    <context-param>
        <param-name>user</param-name>
        <param-value>your email address</param-value>
    </context-param>
 
    <context-param>
        <param-name>pass</param-name>
        <param-value>your email password</param-value>
    </context-param>
```

Good to go!

![TRANGCHU](https://user-images.githubusercontent.com/91520278/209820076-5c0af25c-2ef5-4aae-bf5c-412e7ade6d49.png)
![CUAHANG-BOLOC-SEARCH](https://user-images.githubusercontent.com/91520278/209820096-fc76c155-6123-47ab-b3c5-bc3713c4dd25.png)
![CHITIETSP](https://user-images.githubusercontent.com/91520278/209820109-cbf5f557-32af-45bb-bf5d-181e6c69dd21.png)
![DANHGIA](https://user-images.githubusercontent.com/91520278/209820124-6ac7c9ce-c582-4856-bc4d-8ed806e1b235.png)
![GIOHANG](https://user-images.githubusercontent.com/91520278/209820135-8c899eba-5204-4a0e-8f03-b382756fd40a.png)
![THANHTOAN2](https://user-images.githubusercontent.com/91520278/209820157-33b3d43a-fac0-404c-8af3-94151f6ec0ce.png)
![ADMIN-CHART](https://user-images.githubusercontent.com/91520278/209820169-37e37126-4c39-47f0-898a-b34612a3d354.png)
