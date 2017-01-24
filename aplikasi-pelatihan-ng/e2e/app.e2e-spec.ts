import { AplikasiPelatihanNgPage } from './app.po';

describe('aplikasi-pelatihan-ng App', function() {
  let page: AplikasiPelatihanNgPage;

  beforeEach(() => {
    page = new AplikasiPelatihanNgPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
