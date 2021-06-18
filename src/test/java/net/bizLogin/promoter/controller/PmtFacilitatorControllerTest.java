package net.bizLogin.promoter.controller;

import net.bizLogin.promoter.service.PmtFacilitatorService;
import net.bizLogin.promoter.vo.BizPromoter_VO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PmtFacilitatorControllerTest {

    @InjectMocks
    PmtFacilitatorController controller;

    @Mock
    PmtFacilitatorService pmtFacilitatorService;

    MockMvc mockMvc;

    @Before
    public void setup(){
        assertThat(controller).isNotNull();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void selectBizPmtLogin() throws Exception {
        // given
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pmtLoginId", "mwavtest");
        map.put("pmtLoginPw", "mwav123!@#");

        BizPromoter_VO bizPromoterVo = new BizPromoter_VO();
        bizPromoterVo.setPmtLoginId((String) map.get("pmtLoginId"));
        bizPromoterVo.setPmtLoginPw((String) map.get("pmtLoginPw"));
        bizPromoterVo.setPmtCertifyDt(null);

        // when
        when(pmtFacilitatorService.selectBizPmtLogin(any())).thenReturn(bizPromoterVo);

        // then
        // 테스트는 간단하게하여 리다이렉션 처리가 정상적으로 이루어지는지 확인함
        mockMvc.perform(post("/bizLogin/promoter/facilitator/pmtFacilitatorLogin.mwav"))
                .andDo(print())
                .andExpect(flash().attributeExists("msg"))
                .andExpect(status().is(302));
    }

}