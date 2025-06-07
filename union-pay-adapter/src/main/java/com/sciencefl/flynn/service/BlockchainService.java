package com.sciencefl.flynn.service;

import com.sciencefl.flynn.dto.ApplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockchainService {

    public void  processMessage(ApplyDTO applyDTO, String messageType) {
        // 根据消息类型调用不同的区块链合约方法
//        switch (messageType) {
//            case MessageTypeConstants.APPLY:
//                apply(applyDTO);
//                break;
//            case MessageTypeConstants.USE:
//                use(applyDTO);
//                break;
//            case MessageTypeConstants.REFUND:
//                refund(applyDTO);
//                break;
//            case MessageTypeConstants.RETURN:
//                returnCoupon(applyDTO);
//                break;
//            default:
//                log.warn("未知消息类型: {}", messageType);
//        }

    }
//    private final Web3j web3j;
//    private final Credentials credentials;
//    private final ContractGasProvider gasProvider;
//
//    @Value("${blockchain.contract-address}")
//    private String contractAddress;
//
//    public void callContract(String functionName, List<Object> params) {
//        try {
//            Function function = new Function(
//                    functionName,
//                    params.stream()
//                            .map(p -> new Utf8String(p.toString()))
//                            .collect(Collectors.toList()),
//                    Collections.emptyList()
//            );
//
//            String encodedFunction = FunctionEncoder.encode(function);
//            RawTransaction rawTransaction = RawTransaction.createTransaction(
//                    web3j.ethChainId().send().getChainId(),
//                    gasProvider.getGasPrice(),
//                    gasProvider.getGasLimit(),
//                    contractAddress,
//                    encodedFunction
//            );
//
//            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//            String hexValue = Numeric.toHexString(signedMessage);
//
//            EthSendTransaction transactionResponse = web3j.ethSendRawTransaction(hexValue).send();
//            if (transactionResponse.hasError()) {
//                throw new BusinessException("合约调用失败: " + transactionResponse.getError().getMessage());
//            }
//            log.info("交易已提交，哈希: {}", transactionResponse.getTransactionHash());
//        } catch (Exception e) {
//            throw new BusinessException("合约调用异常: " + e.getMessage());
//        }
//    }
}
