package se.rosscom.shopper.business.authentication.boundary;

import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.authentication.entity.Token;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Stateless
public class TokenService {

    @PersistenceContext
    private EntityManager em;

    public String generateToken(Account account) {
        Token token = this.createToken(account);
        return "Auth-shopper " + account.getUserId() +":" + token.getToken();
    }

    public Token createToken(final Account account) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);

        Token tokenEntity = new Token();
        tokenEntity.setAccount(account);
        tokenEntity.setToken(token);
        tokenEntity.setExpirationDate(LocalDateTime.now().plusDays(1));
        em.persist(tokenEntity);

        return tokenEntity;
    }

    public Token findByTokenAndUser(final String token, final String userId) {
        List<Token> resultList = em.createQuery("SELECT a FROM Token a WHERE a.token = :token AND a.account.userId = :userId"
                , Token.class)
                .setParameter("token", token)
                .setParameter("userId", userId)
                .getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
