package com.xd.springbootdemo.test;

import com.xd.springbootdemo.util.HttpUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 核算放款测试类
 *
 * @author admin
 */
public class TestHs {

    @Test
    public void test10000000022LoanDown() {
        // 第一步：将requestMessage转换为xml的字符串
        List<String> msgList = batchGenData(1);
        int i = 1;
        for (String reqMessage : msgList) {
            // 第二步：将根据请求报文，调用核算接口
            System.out.println("第"+ i + "次HsLoanDownTest[请求报文]" + reqMessage);
            String rspMessage = HttpUtil.callHsMethod(reqMessage);
            // 响应报文字符串
            System.out.println("第"+ i + "次HsLoanDownTest[响应报文]" + rspMessage);
            i++;
        }
    }

    /**
     * 批量生成报文数据
     *
     * @param size 数据条数
     * @return 报文集合
     */
    public static List<String> batchGenData(int size) {
        long start = 202407150742143205L;//根据时间戳随便设定一个起始值，后续依次加1
        String GEN_GL_NO = start + "";//需修改：放款授权号，每次发起新请求，需要修改成一个新的号码;
        String appl_seq = GEN_GL_NO;//需修改：申请借据表的seq，需要修改成一个新的号码 ;
        String appl_cde = "QD" + GEN_GL_NO;//需修改：申请借据表的申请编号，需要修改成一个新的号码;
        String releaseDate = "2024-06-01"; // 需修改：放款日期；需要改成和本地glloans.s_ctrl当期要一致;
        String DUE_DAY = releaseDate.substring(8);// 取放款日期对应的后两位（日）
        String TNR = "3";//可修改：借据期数
        String endDate = "";//TNR有值时，可以传空
        String id_no = "410725199605310011";//可修改：身份证号
        String cust_name = "张九";//可修改：借款人名
        String loan_typ = "YGD_005";//可修改：贷款品种号
        String PAYM_FREQ_UNIT = "M";//间隔单位按月
        String PAYM_FREQ_FREQ = "1";//间隔

//		还款方式类型：
//		00  自由还款法（不确定还款日）
//		01	等额本息
//		02  本息不同间隔
//		03	等额本金
//		04	按期结息
//		05  利随本清
//		07	弹性还款（分阶段）
//		08	气球贷
//		对应还款方式中的P_LOAN_MTD.MTD_TYP
//		09 其他
        String LOAN_PAYM_TYP = "01";
        String LOAN_PAYM_MTD = "SYS002"; //还款方式，对应P_LOAN_MTD. MTD_CDE，该方式代码必须在还款方式配置表p_loan_mtd中已经配置，后台程序通过查询方式表构造每个借据的还款方式

        List<String> dataList = new ArrayList<String>();
        if (size > 1) {
            for (int i = 0; i < size; i++) {
                GEN_GL_NO = start + "";
                appl_seq = start + "";
                appl_cde = "QD" + start + "";
                dataList.add(genReqMessage(GEN_GL_NO, appl_seq, appl_cde, releaseDate, DUE_DAY, TNR, endDate, id_no,
                        cust_name, PAYM_FREQ_UNIT, LOAN_PAYM_TYP, loan_typ, PAYM_FREQ_FREQ, LOAN_PAYM_MTD));
                start++;
            }
        } else {
            dataList.add(genReqMessage(GEN_GL_NO, appl_seq, appl_cde, releaseDate, DUE_DAY, TNR, endDate, id_no,
                    cust_name, PAYM_FREQ_UNIT, LOAN_PAYM_TYP, loan_typ, PAYM_FREQ_FREQ, LOAN_PAYM_MTD));
        }

        return dataList;
    }

    /**
     * 生成核算放款报文
     *
     * @return 报文
     */
    public static String genReqMessage(String GEN_GL_NO, String appl_seq, String appl_cde, String releaseDate, String DUE_DAY,
                                       String TNR, String endDate, String id_no, String cust_name, String PAYM_FREQ_UNIT,
                                       String LOAN_PAYM_TYP, String loan_typ, String PAYM_FREQ_FREQ, String LOAN_PAYM_MTD) {
        final String reqHead = "XXXXX;serv10000000022;"; // 不能修改：核算报文头

        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        //利率模式 FX: 固定利率 RV: 浮动利率
        //利率计算基础 Y: 年利率 M: 月利率  D: 日利率
        StringBuilder bodyBuff = new StringBuilder("<?xml version=\"1.0\" encoding=\"gbk\" standalone=\"yes\"?>\n").
                append("<msgbody>\n").
                append("<serviceId>serv10000000022</serviceId>\n").
                append("<GEN_GL_NO>").append(GEN_GL_NO).append("</GEN_GL_NO>\n").
                append("<APPL_SEQ>").append(appl_seq).append("</APPL_SEQ>\n").
                append("<INPUT_DT>").append(releaseDate).append("</INPUT_DT>\n").
                append("<LOAN_CONT_NO>HT").append(appl_cde).append("</LOAN_CONT_NO>\n").
                append("<BCH_CDE>900000000</BCH_CDE>\n").
                append("<LOAN_NO>HT").append(appl_cde).append("001</LOAN_NO>\n").
                append("<BANK_CDE>900000000</BANK_CDE>\n").
                append("<CUST_ID>250719289537986560</CUST_ID>\n").
                append("<CUST_NAME>").append(cust_name).append("</CUST_NAME>\n").
                append("<ID_TYPE>20</ID_TYPE>\n").append("<ID_NO>").append(id_no).append("</ID_NO>\n").
                append("<ISS_CTRY>CN</ISS_CTRY>\n").
                append("<DEALER_CDE></DEALER_CDE>\n").
                append("<LOAN_CCY>CNY</LOAN_CCY>\n").
                append("<ORIG_PRCP>20000.00</ORIG_PRCP>\n").
                append("<LOAN_ACTV_DT>").append(releaseDate).append("</LOAN_ACTV_DT>\n").
                append("<TYP_GRP>02</TYP_GRP>\n").
                append("<LOAN_TYP>").append(loan_typ).append("</LOAN_TYP>\n").
                append("<LAST_DUE_DT>").append(endDate).append("</LAST_DUE_DT>\n").
                append("<INT_START_DT>").append(releaseDate).append("</INT_START_DT>\n").
                append("<DUE_DAY>").append(DUE_DAY).append("</DUE_DAY>\n").
                append("<LOAN_RATE_MODE>FX</LOAN_RATE_MODE>\n").//利率模式 FX: 固定利率 RV: 浮动利率
                append("<RATE_BASE>Y</RATE_BASE>\n").//利率计算基础 Y: 年利率 M: 月利率  D: 日利率
                append("<LOAN_RATE_TYP>RAT12</LOAN_RATE_TYP>\n").
                append("<LOAN_BASE_RATE>0.043300000</LOAN_BASE_RATE>\n").
                append("<INT_ADJ_PCT>0.00000</INT_ADJ_PCT>\n").
                append("<LOAN_SPRD>-0.042670000</LOAN_SPRD>\n").
                append("<LOAN_INT_RATE>0.229950000</LOAN_INT_RATE>\n").
                append("<OD_RATE_BASE>Y</OD_RATE_BASE>\n").
                append("<LOAN_OD_RATE_TYP></LOAN_OD_RATE_TYP>\n").
                append("<LOAN_OD_BASE_RATE>0.365000000</LOAN_OD_BASE_RATE>\n").
                append("<OD_RATE_SPRD>0</OD_RATE_SPRD>\n").
                append("<LOAN_OD_INT_RATE>0.365000000</LOAN_OD_INT_RATE>\n").
                append("<PAYM_FREQ_UNIT>").append(PAYM_FREQ_UNIT).append("</PAYM_FREQ_UNIT>\n").
                append("<PAYM_FREQ_FREQ>").append(PAYM_FREQ_FREQ).append("</PAYM_FREQ_FREQ>\n").
                append("<LOAN_PAYM_MTD>").append(LOAN_PAYM_MTD).append("</LOAN_PAYM_MTD>\n").
                append("<LOAN_PAYM_TYP>").append(LOAN_PAYM_TYP).append("</LOAN_PAYM_TYP>\n").
                append("<BUSS_TYP>NLOAN</BUSS_TYP>\n").
                append("<LOAN_GRACE_TYP>P</LOAN_GRACE_TYP>\n").
                append("<LOAN_OD_GRACE>3</LOAN_OD_GRACE>\n").
                append("<LOAN_REPAY_MTHD>AUTOPAY</LOAN_REPAY_MTHD>\n").
                append("<LOAN_FIX_OD_INT_IND>Y</LOAN_FIX_OD_INT_IND>\n").
                append("<LOAN_OD_CAT>L</LOAN_OD_CAT>\n").
                append("<LOAN_OD_RATE_ADJ_PCT>0</LOAN_OD_RATE_ADJ_PCT>\n").
                append("<LOAN_OD_COMM_PART>P</LOAN_OD_COMM_PART>\n").
                append("<LOAN_OD_CPD_IND>Y</LOAN_OD_CPD_IND>\n").
                append("<NEXT_REPC_OPT></NEXT_REPC_OPT>\n").
                append("<NEXT_REPC_NUM></NEXT_REPC_NUM>\n").
                append("<NEXT_REPC_UNIT></NEXT_REPC_UNIT>\n").
                append("<DIVER_ADJ_PCT>1.00000</DIVER_ADJ_PCT>\n").
                append("<FIRST_REPC_DT>").append(releaseDate).append("</FIRST_REPC_DT>\n").
                append("<LOAN_OD_INT_RATE1>0.365000000</LOAN_OD_INT_RATE1>\n").
                append("<LOAN_OD_RATE_ADJ_PCT1>0</LOAN_OD_RATE_ADJ_PCT1>\n").
                append("<PRCP_BUY_IND>N</PRCP_BUY_IND>\n").
                append("<INSTM_IND>Y</INSTM_IND>\n").
                append("<APP_USER_ID>admin</APP_USER_ID>\n").
                append("<APP_USER_NAME>admin</APP_USER_NAME>\n").
                append("<APP_USER_ORG_NO>900000000</APP_USER_ORG_NO>\n").
                append("<LmPmShdTList>\n").
                append("<MX>\n").
                append("<LOAN_NO></LOAN_NO>\n").
                append("<PS_PERD_NO></PS_PERD_NO>\n").
                append("<PS_DUE_DT></PS_DUE_DT>\n").
                append("<PS_PRCP_AMT></PS_PRCP_AMT>\n").
                append("<PS_NORM_INT_AMT></PS_NORM_INT_AMT>\n").
                append("</MX>\n").
                append("</LmPmShdTList>\n").
                append("<LmAcctInfoTList>\n").
                append("<MX>\n").
                append("<GEN_GL_NO>").append(GEN_GL_NO).append("</GEN_GL_NO>\n").
                append("<LOAN_NO>HT").append(appl_cde).append("001</LOAN_NO>\n").
                append("<LOAN_ACCT_TYP>ACTV</LOAN_ACCT_TYP>\n").
                append("<ACCT_NO>6214853713666502</ACCT_NO>\n").
                append("<ACCT_CCY_CDE>CNY</ACCT_CCY_CDE>\n").
                append("<ACCT_BANK_CDE>308</ACCT_BANK_CDE>\n").
                append("<ACCT_BCH_CDE></ACCT_BCH_CDE>\n").
                append("<ACCT_NAME>").append(cust_name).append("</ACCT_NAME>\n").
                append("<ACCT_ISS_CTRY>CN</ACCT_ISS_CTRY>\n").
                append("<ACCT_TYP>01</ACCT_TYP>\n").
                append("<ACCT_ID_TYPE>20</ACCT_ID_TYPE>\n").
                append("<ACCT_ID_NO>").append(id_no).append("</ACCT_ID_NO>\n").
                append("<ACCT_CARD_NO>6214853713660001</ACCT_CARD_NO>\n").
                append("<ACCT_BCH_NAM>招商银行</ACCT_BCH_NAM>\n").
                append("<ACCT_BANK_ADD>广东省/深圳市</ACCT_BANK_ADD>\n").
                append("<ACCT_BANK_NAM>招商银行</ACCT_BANK_NAM>\n").
                append("<BANK_PROV>广东省</BANK_PROV>\n").
                append("<BANK_CITY>深圳市</BANK_CITY>\n").
                append("</MX>\n").
                append("<MX>\n").
                append("<GEN_GL_NO>").append(GEN_GL_NO).append("</GEN_GL_NO>\n").
                append("<LOAN_NO>HT").append(appl_cde).append("001</LOAN_NO>\n").
                append("<LOAN_ACCT_TYP>PAYM</LOAN_ACCT_TYP>\n").
                append("<ACCT_NO>6214853713660001</ACCT_NO>\n").
                append("<ACCT_CCY_CDE>CNY</ACCT_CCY_CDE>\n").
                append("<ACCT_BANK_CDE>308</ACCT_BANK_CDE>\n").
                append("<ACCT_BCH_CDE></ACCT_BCH_CDE>\n").
                append("<ACCT_NAME>").append(cust_name).append("</ACCT_NAME>\n").
                append("<ACCT_ISS_CTRY>CN</ACCT_ISS_CTRY>\n").
                append("<ACCT_TYP>01</ACCT_TYP>\n").
                append("<ACCT_ID_TYPE>20</ACCT_ID_TYPE>\n").
                append("<ACCT_ID_NO>").append(id_no).append("</ACCT_ID_NO>\n").
                append("<ACCT_CARD_NO>6214853713660001</ACCT_CARD_NO>\n").
                append("<ACCT_BCH_NAM>招商银行</ACCT_BCH_NAM>\n").
                append("<ACCT_BANK_ADD>广东省/深圳市</ACCT_BANK_ADD>\n").
                append("<ACCT_BANK_NAM>招商银行</ACCT_BANK_NAM>\n").
                append("<BANK_PROV>广东省</BANK_PROV>\n").
                append("<BANK_CITY>深圳市</BANK_CITY>\n").
                append("</MX>\n").
                append("</LmAcctInfoTList>\n").
                append("<LmFeeTxTList>\n").
                append("<MX>\n").
                append("<GEN_GL_NO>").append(GEN_GL_NO).append("</GEN_GL_NO>\n").
                append("<LOAN_NO>HT").append(appl_cde).append("001</LOAN_NO>\n").
                append("<FEE_CDE>zyd001</FEE_CDE>\n").
                append("<FEE_CCY_CDE>CNY</FEE_CCY_CDE>\n").
                append("<FEE_AMT>160.30</FEE_AMT>\n").
                append("<RECV_PAY_IND>R</RECV_PAY_IND>\n").
                append("<FEE_TYP>06</FEE_TYP>\n").
                append("<FEE_CALC_TYP>PCT</FEE_CALC_TYP>\n").
                append("<BASE_AMT>10000.00</BASE_AMT>\n").
                append("<CHRG_PCT>0.016030000</CHRG_PCT>\n").
                append("<LOAN_ACCT_TYP>PAYM</LOAN_ACCT_TYP>\n").
                append("<FEE_ACCT_NO>6214853713660001</FEE_ACCT_NO>\n").
                append("<HOLD_FEE_IND>Y</HOLD_FEE_IND>\n").
                append("<PAYM_FREQ_UNIT>M</PAYM_FREQ_UNIT>\n").
                append("<FEE_SPAN>1</FEE_SPAN>\n").
                append("<FEE_STR_PERD_NO>1</FEE_STR_PERD_NO>\n").
                append("<HOLD_FEE_SETL_DT>").append(endDate).append("</HOLD_FEE_SETL_DT>\n").
                append("<ACC_IND>N</ACC_IND>\n").
                append("<CALC_ACC_IND>Y</CALC_ACC_IND>\n").
                append("<ACC_AMT_TYP>F01</ACC_AMT_TYP>\n").
                append("<RELEASE_ACC_MAIN></RELEASE_ACC_MAIN>\n").
                append("<IS_EXTERNAL_CHANNEL>2</IS_EXTERNAL_CHANNEL>\n").
                append("</MX>\n").
                append("<MX>\n").
                append("<GEN_GL_NO>").append(GEN_GL_NO).append("</GEN_GL_NO>\n").
                append("<LOAN_NO>HT").append(appl_cde).append("001</LOAN_NO>\n").
                append("<FEE_CDE>zyd001</FEE_CDE>\n").
                append("<FEE_CCY_CDE>CNY</FEE_CCY_CDE>\n").
                append("<FEE_AMT>100.5</FEE_AMT>\n").
                append("<RECV_PAY_IND>R</RECV_PAY_IND>\n").
                append("<FEE_TYP>06</FEE_TYP>\n").
                append("<FEE_CALC_TYP>PCT</FEE_CALC_TYP>\n").
                append("<BASE_AMT>10000.00</BASE_AMT>\n").
                append("<CHRG_PCT>0.016030000</CHRG_PCT>\n").
                append("<LOAN_ACCT_TYP>PAYM</LOAN_ACCT_TYP>\n").
                append("<FEE_ACCT_NO>6214853713660001</FEE_ACCT_NO>\n").
                append("<HOLD_FEE_IND>Y</HOLD_FEE_IND>\n").
                append("<PAYM_FREQ_UNIT>M</PAYM_FREQ_UNIT>\n").
                append("<FEE_SPAN>2</FEE_SPAN>\n").
                append("<FEE_STR_PERD_NO>1</FEE_STR_PERD_NO>\n").
                append("<HOLD_FEE_SETL_DT>").append(endDate).append("</HOLD_FEE_SETL_DT>\n").
                append("<ACC_IND>N</ACC_IND>\n").
                append("<CALC_ACC_IND>Y</CALC_ACC_IND>\n").
                append("<ACC_AMT_TYP>F01</ACC_AMT_TYP>\n").
                append("<RELEASE_ACC_MAIN></RELEASE_ACC_MAIN>\n").
                append("<IS_EXTERNAL_CHANNEL>2</IS_EXTERNAL_CHANNEL>\n").
                append("</MX>\n").
                append("</LmFeeTxTList>\n").
                append("<LmDnShdMtdTList>\n").
                append("<MX>\n").
                append("<TERM_NO>1</TERM_NO>\n").
                append("<FRM_PERD>1</FRM_PERD>\n").
                append("<TO_PERD>").append(TNR).append("</TO_PERD>\n").
                append("<PRCP_AMT></PRCP_AMT>\n").
                append("<PAYM_FREQ_UNIT>").append(PAYM_FREQ_UNIT).append("</PAYM_FREQ_UNIT>\n").
                append("<PAYM_FREQ_FREQ>").append(PAYM_FREQ_FREQ).append("</PAYM_FREQ_FREQ>\n").
                append("<LOAN_INT_RATE>0.109500000</LOAN_INT_RATE>\n").
                append("<PAYM_TYP>T</PAYM_TYP>\n").
                append("<PAYM_OPT>IP</PAYM_OPT>\n").
                append("<TERM_PRCP_AMT></TERM_PRCP_AMT>\n").
                append("<CAL_TOT_INSTM>").append(TNR).append("</CAL_TOT_INSTM>\n").
                append("<PRCP_PCT></PRCP_PCT>\n").
                append("<RAT_BASE>D</RAT_BASE>\n").
                append("<INT_START_DT>").append(releaseDate).append("</INT_START_DT>\n").
                append("<LOAN_OD_INT_RATE>0.0010000</LOAN_OD_INT_RATE>\n").
                append("<BASE_RATE>0.043300000</BASE_RATE>\n").
                append("<RATE_TYP>RAT12</RATE_TYP>\n").
                append("<SPRD>-0.042670000</SPRD>\n").
                append("<INT_ADJ_PCT>0.00000</INT_ADJ_PCT>\n").
                append("</MX>\n").
                append("</LmDnShdMtdTList>\n").
                append("<TYP_VER>1</TYP_VER>\n").
                append("<TNR>").append(TNR).append("</TNR>\n").
                append("<OLD_LOAN_NO></OLD_LOAN_NO>\n").
                append("<PAY_TYP></PAY_TYP>\n").
                append("<SBSY_IND>N</SBSY_IND>\n").
                append("<SBSY_INT_RATE>0</SBSY_INT_RATE>\n").
                append("<DN_CHANNEL>02</DN_CHANNEL>\n").
                append("<PAYM_CHANNEL>00</PAYM_CHANNEL>\n").
                append("<THIRD_TRADE_STS>0</THIRD_TRADE_STS>\n").
                append("<BATCH_NO>").append(GEN_GL_NO).append("</BATCH_NO>\n").
                append("<ORDER_ID>msxd0001").append(GEN_GL_NO).append("000</ORDER_ID>\n").
                append("<MSG_EXT></MSG_EXT>\n").
                append("<FIRPAY_PRCP>0</FIRPAY_PRCP>\n").
                append("<FINANCING_SOURCE>1</FINANCING_SOURCE>\n").
                append("<REPAY_SOURCE>1</REPAY_SOURCE>\n").
                append("<YD_ACC_ID></YD_ACC_ID>\n").
                append("<DAIFU_MER_NO>msxd0001</DAIFU_MER_NO>\n").
                append("<DAISHOU_MER_NO>msxd0001</DAISHOU_MER_NO>\n").
                append("<RELEASE_ACC_TYPE>1</RELEASE_ACC_TYPE>\n").
                append("<REPAY_ACC_TYPE>1</REPAY_ACC_TYPE>\n").
                append("<IS_MDL_RECORD>0</IS_MDL_RECORD>\n").
                append("<REPAYMENT_PLAN_SOURCE>02</REPAYMENT_PLAN_SOURCE>\n").
                append("<WITHHOLD_CONTENT>03</WITHHOLD_CONTENT>\n").
                append("</msgbody>\n");

        return reqHead + bodyBuff.toString();
    }

}
