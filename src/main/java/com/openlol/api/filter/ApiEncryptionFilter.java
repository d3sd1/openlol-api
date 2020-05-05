package com.openlol.api.filter;


//@WebFilter(urlPatterns = "/api/*")
//@Component
//@Order(1)
public class ApiEncryptionFilter /*implements Filter*/ {
/*

    @Autowired
    private ObjectMapper objectMapper;

    AppAuthService appAuthService = new AppAuthService();
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.out.println("Request URI is: " + req.getRequestURI());
        try {
            EncryptedTransaction encryptedTransaction = this.objectMapper.readValue(
                    Base64.getDecoder().decode(req.getHeader("X-openlol-ver")),
                    EncryptedTransaction.class
            );
            User user = appAuthService.getUser(encryptedTransaction.getUserId());
            String encAesKeyBase64 = encryptedTransaction.getEncAesKey();
            byte[] encAesKeyBytes = Base64.getDecoder().decode(encAesKeyBase64);
            byte[] decryptedAesKeyHex =
                    CryptoUtil.decryptWithPrivateRsaKey(encAesKeyBytes, user.getRsaPrivateKey());
            byte[] decryptedAesKey = HexUtils.fromHexString(new String(decryptedAesKeyHex));
            System.out.printf("Decrypted Aes Key [len=%d]: %s\n", decryptedAesKey.length, new String(decryptedAesKey));
            byte[] iv = ("1234567890123456").getBytes();
            byte[] encTransBytes = Base64.getDecoder().decode(encryptedTransaction.getPayload());
            byte[] decrypted = CryptoUtil.decryptWithAes(encTransBytes, decryptedAesKey, iv);
            Transaction transaction = this.objectMapper.readValue(new String(decrypted), Transaction.class);
            chain.doFilter(request, response);
        } catch (Exception e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication.");
        }
        System.out.println("Response Status Code is: " + res.getStatus());
    }*/
}
