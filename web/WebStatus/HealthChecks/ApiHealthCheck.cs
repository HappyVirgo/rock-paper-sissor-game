using System.Net.Http;

namespace WebStatus.HealthChecks
{
    public class ApiHealthCheck : IHealthCheck
    {
        public IHttpClientFactory HttpClientFactory { get; }
        public string ApiPath { get; }

        public ApiHealthCheck(IHttpClientFactory httpClientFactory, string apiPath)
        {
            HttpClientFactory = httpClientFactory;
            ApiPath = apiPath;
        }
        public async Task<HealthCheckResult> CheckHealthAsync(HealthCheckContext context,
            CancellationToken cancellationToken = default)
        {
            using var httpClient = HttpClientFactory.CreateClient();
            var response = await httpClient.GetAsync(ApiPath, cancellationToken);
            if (response.IsSuccessStatusCode)
            {
                return HealthCheckResult.Healthy("API is running...");
            }
            return HealthCheckResult.Unhealthy("API is not running...");
        }
    }
}
