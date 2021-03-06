package com.xeiam.xchange.kraken.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPair;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenFee;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;

public class KrakenAssetPairsJSONTest {

  private KrakenAssetPair expectedAssetPairInfo;

  @Before
  public void before() {

    List<KrakenFee> fees = new ArrayList<KrakenFee>();
    fees.add(new KrakenFee(new BigDecimal("0"), new BigDecimal("0.3")));
    expectedAssetPairInfo = new KrakenAssetPair("XBTUSD", "currency", "XXBT", "currency", "ZUSD", "unit", 5, 8, new BigDecimal(1),
        new ArrayList<String>(), fees, "ZUSD", new BigDecimal(80), new BigDecimal(40));
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAssetPairsJSONTest.class.getResourceAsStream("/marketdata/example-assetpairs-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenAssetPairsResult krakenAssetPairs = mapper.readValue(is, KrakenAssetPairsResult.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(krakenAssetPairs.getResult().get("XXBTZEUR")).isNotNull();
    assertThat(krakenAssetPairs.getResult().get("XBTCEUR")).isNull();

    KrakenAssetPair krakenAssetPairInfo = krakenAssetPairs.getResult().get("XXBTZUSD");
    assertThat(krakenAssetPairInfo.getAltName()).isEqualTo(expectedAssetPairInfo.getAltName());
    assertThat(krakenAssetPairInfo.getBase()).isEqualTo(expectedAssetPairInfo.getBase());
    assertThat(krakenAssetPairInfo.getClassBase()).isEqualTo(expectedAssetPairInfo.getClassBase());
    assertThat(krakenAssetPairInfo.getClassQuote()).isEqualTo(expectedAssetPairInfo.getClassQuote());
    assertThat(krakenAssetPairInfo.getFeeVolumeCurrency()).isEqualTo(expectedAssetPairInfo.getFeeVolumeCurrency());
    assertThat(krakenAssetPairInfo.getLeverage()).isEqualTo(expectedAssetPairInfo.getLeverage());
    assertThat(krakenAssetPairInfo.getQuote()).isEqualTo(expectedAssetPairInfo.getQuote());
    assertThat(krakenAssetPairInfo.getVolumeLotSize()).isEqualTo(expectedAssetPairInfo.getVolumeLotSize());
    assertThat(krakenAssetPairInfo.getPairScale()).isEqualTo(expectedAssetPairInfo.getPairScale());
    assertThat(krakenAssetPairInfo.getVolumeLotScale()).isEqualTo(expectedAssetPairInfo.getVolumeLotScale());
    assertThat(krakenAssetPairInfo.getMarginCall()).isEqualTo(expectedAssetPairInfo.getMarginCall());
    assertThat(krakenAssetPairInfo.getMarginStop()).isEqualTo(expectedAssetPairInfo.getMarginStop());
    assertThat(krakenAssetPairInfo.getVolumeMultiplier()).isEqualTo(expectedAssetPairInfo.getVolumeMultiplier());
    assertThat(krakenAssetPairInfo.getFees().size()).isEqualTo(26);

    KrakenFee deserializedFee = krakenAssetPairInfo.getFees().get(0);
    KrakenFee expectedFee = expectedAssetPairInfo.getFees().get(0);
    assertThat(deserializedFee.getPercentFee()).isEqualTo(expectedFee.getPercentFee());
    assertThat(deserializedFee.getVolume()).isEqualTo(expectedFee.getVolume());
  }
}
