import { IImgUrl } from 'app/shared/model/img-url.model';
import { IServiceOption } from 'app/shared/model/service-option.model';
import { IProcess } from 'app/shared/model/process.model';
import { IBlog } from 'app/shared/model/blog.model';
import { IVideo } from 'app/shared/model/video.model';
import { IPricingCard } from 'app/shared/model/pricing-card.model';

export interface IServiceItem {
  id?: number;
  name?: string;
  description?: string;
  imgUrl?: IImgUrl;
  options?: IServiceOption[];
  processes?: IProcess[];
  relatedBlogs?: IBlog[];
  relatedVideos?: IVideo[];
  customerImgUrls?: IImgUrl[];
  pricingCards?: IPricingCard[];
}

export const defaultValue: Readonly<IServiceItem> = {};
