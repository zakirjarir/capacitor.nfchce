import { WebPlugin, PluginListenerHandle } from '@capacitor/core';

import type { NfcHcePlugin } from './definitions';

export class NfcHceWeb extends WebPlugin implements NfcHcePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async startHce(options: { aid: string }): Promise<void> {
    console.warn('NFC HCE is not available on the web.', options);
    throw this.unimplemented('NFC HCE is not available on the web.');
  }

  async stopHce(): Promise<void> {
    console.warn('NFC HCE is not available on the web.');
    throw this.unimplemented('NFC HCE is not available on the web.');
  }

  async setCardData(options: { data: string }): Promise<void> {
    console.warn('NFC HCE is not available on the web.', options);
    throw this.unimplemented('NFC HCE is not available on the web.');
  }

  async addListener(
    eventName: 'cardDataReceived',
    listenerFunc: (data: { value: string }) => void,
  ): Promise<PluginListenerHandle> {
    console.warn('NFC HCE is not available on the web.');
    throw this.unimplemented('NFC HCE is not available on the web.');
  }
}
