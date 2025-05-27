package com.sciencefl.flynn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {
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
