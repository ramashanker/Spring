import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export const rxStompConfig: InjectableRxStompConfig = {
  brokerURL: `ws://${window.location.host}/connect`,
  reconnectDelay: 10000
};
