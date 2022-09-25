using System.Net.Http;
using System.Threading.Tasks;
using System.Threading;

namespace WebStatus.HealthChecks
{
    public class ApiHealthCheck : IHealthCheck
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly string _apiPath;

        public ApiHealthCheck(IHttpClientFactory httpClientFactory, string apiPath)
        {
            _httpClientFactory = httpClientFactory;
            _apiPath = apiPath;
        }
        public async Task<HealthCheckResult> CheckHealthAsync(HealthCheckContext context,
            CancellationToken cancellationToken = default)
        {
            using var httpClient = _httpClientFactory.CreateClient();
            var response = await httpClient.GetAsync(_apiPath, cancellationToken);
            if (response.IsSuccessStatusCode)
            {
                return HealthCheckResult.Healthy("API is running...");
            }
            return HealthCheckResult.Unhealthy("API is not running...");
        }
    }
}
