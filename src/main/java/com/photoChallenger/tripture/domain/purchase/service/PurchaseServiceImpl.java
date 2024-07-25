package com.photoChallenger.tripture.domain.purchase.service;

import com.photoChallenger.tripture.domain.item.repository.ItemRepository;
import com.photoChallenger.tripture.domain.login.entity.Login;
import com.photoChallenger.tripture.domain.login.repository.LoginRepository;
import com.photoChallenger.tripture.domain.profile.entity.Profile;
import com.photoChallenger.tripture.domain.profile.repository.ProfileRepository;
import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemDto;
import com.photoChallenger.tripture.domain.purchase.dto.PurchaseItemResponse;
import com.photoChallenger.tripture.domain.purchase.entity.Purchase;
import com.photoChallenger.tripture.domain.purchase.repository.PurchaseRepository;
import com.photoChallenger.tripture.global.exception.purchase.AlreadyUsedItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{
    private final LoginRepository loginRepository;
    private final PurchaseRepository purchaseRepository;
    public List<PurchaseItemResponse> checkItemsBeforeUse(long loginId) {
        Login login = loginRepository.findById(loginId).get();
        Profile profile = login.getProfile();
        List<Purchase> purchaseList = profile.getPurchases();
        List<PurchaseItemResponse> purchaseItemResponseList = new ArrayList<>();
        for(Purchase p : purchaseList){
            if( p.getPurchaseCheck() == false){ //사용 전
                purchaseItemResponseList.add(PurchaseItemResponse.from(p, p.getItem()));
            }
        }
        return purchaseItemResponseList;
    }

    @Override
    public List<PurchaseItemResponse> checkItemsAfterUse(long loginId) {
        Login login = loginRepository.findById(loginId).get();
        Profile profile = login.getProfile();
        List<Purchase> purchaseList = profile.getPurchases();
        List<PurchaseItemResponse> purchaseItemResponseList = new ArrayList<>();
        for(Purchase p : purchaseList){
            if( p.getPurchaseCheck() == true){ //사용 후
                purchaseItemResponseList.add(PurchaseItemResponse.from(p, p.getItem()));
            }
        }
        return purchaseItemResponseList;
    }

    @Override
    public PurchaseItemDto checkDetail(long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        return PurchaseItemDto.from(purchase.getItem(), purchase);
    }

    @Override
    @Transactional
    public void useItem(long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        if(purchase.getPurchaseCheck() == true){
            throw new AlreadyUsedItemException();
        }
            purchase.update(true);
    }
}
