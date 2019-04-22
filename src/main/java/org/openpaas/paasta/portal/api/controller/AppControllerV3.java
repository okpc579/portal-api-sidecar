package org.openpaas.paasta.portal.api.controller;

import org.openpaas.paasta.portal.api.common.Common;
import org.openpaas.paasta.portal.api.service.AppServiceV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppControllerV3 extends Common {

    @Autowired
    private AppServiceV3 appService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AppControllerV3.class);

    /**
     * 앱을 실행한다.
     *
     * @param app     the app
     * @return ModelAndView model
     * @throws Exception the exception
     */
    @RequestMapping(value = {Constants.V3_URL + "/apps/{guid}/actions/start"}, method = RequestMethod.POST)
    public Map startApp(@RequestBody App app, @RequestHeader(AUTHORIZATION_HEADER_KEY) String token) throws Exception {
        LOGGER.info("startApp Start ");
        Map resultMap = appService.startApp(app, token);
        LOGGER.info("startApp End ");
        return resultMap;
    }

    /**
     * 앱을 중지한다.
     *
     * @param app     the app
     * @return ModelAndView model
     * @throws Exception the exception
     */
    @RequestMapping(value = {Constants.V3_URL + "/apps/{guid}/actions/stop"}, method = RequestMethod.POST)
    public Map stopApp(@RequestBody App app, @RequestHeader(AUTHORIZATION_HEADER_KEY) String token) throws Exception {
        LOGGER.info("stopApp Start ");
        Map resultMap = appService.stopApp(app, token);
        LOGGER.info("stopApp End ");
        return resultMap;
    }

    /**
     * 앱 요약 정보를 조회한다.
     *
     * @param guid
     * @return ModelAndView model
     * @throws Exception the exception
     */
    @RequestMapping(value = {Constants.V3_URL + "/apps/{guid}/summary"}, method = RequestMethod.GET)
    public void getAppSummary(@PathVariable String guid, @RequestHeader(AUTHORIZATION_HEADER_KEY) String token) throws Exception {
        LOGGER.info("getAppSummary Start : " + guid);

        ReactorCloudFoundryClient reactorCloudFoundryClient = cloudFoundryClient(tokenProvider(token));
        GetApplicationResponse getApplicationResponse = reactorCloudFoundryClient.applicationsV3().get(org.cloudfoundry.client.v3.applications.GetApplicationRequest.builder().applicationId(guid).build()).block();
        GetApplicationCurrentDropletResponse getApplicationCurrentDropletResponse = reactorCloudFoundryClient.applicationsV3().getCurrentDroplet(GetApplicationCurrentDropletRequest.builder().applicationId(guid).build()).block();
        GetApplicationProcessResponse getApplicationProcessResponse = reactorCloudFoundryClient.applicationsV3().getProcess(GetApplicationProcessRequest.builder().applicationId(guid).build()).block();
        GetApplicationProcessStatisticsResponse processStatisticsResponse = reactorCloudFoundryClient.applicationsV3().getProcessStatistics(GetApplicationProcessStatisticsRequest.builder().applicationId(guid).build()).block();




    }


}
